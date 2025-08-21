package agents.service;

import agents.agent.AgentFactory;
import agents.agent.Tool;
import agents.agent.ToolRegistry;
import agents.dto.AddToolRequest;
import agents.dto.CreateAgentRequest;
import agents.dto.CreateUserRequest;
import agents.entity.*;
import agents.helpers.Conversation;
import agents.helpers.RoutingError;
import agents.helpers.enums.AgentType;
import com.google.adk.agents.*;
import com.google.adk.artifacts.InMemoryArtifactService;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import agents.entity.repos.AgentRepository;
import com.google.adk.events.Event;
import agents.entity.repos.UserRepository;
import agents.entity.repos.UserSessionRepository;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentService.class);

    @Autowired
    private final AgentRepository agentRepo;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserSessionRepository userSessionRepository;
    @Autowired
    private final ToolRegistry toolRegistry;
    @Autowired
    private final AgentFactory agentFactory;


    public AgentService(AgentRepository agentRepo, ToolRegistry toolRegistry, AgentFactory agentFactory, UserRepository userRepository, UserSessionRepository userSessionRepository) {
        this.agentRepo = agentRepo;
        this.toolRegistry = toolRegistry;
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.agentFactory = agentFactory;
    }

    @Transactional
    public String createAgent(CreateAgentRequest req) {
        Agent entity = new Agent();
        String uuid = UUID.randomUUID().toString();
        entity.setAgentId(uuid);
        entity.setName(req.name());
        entity.setDescription(req.description());
        entity.setModel(req.model());
        entity.setInstruction(req.instruction());
        agentRepo.save(entity);
        return uuid;
    }

    @Transactional
    public String createUser(CreateUserRequest req) {
        User user1 = userRepository.findByEmail(req.email());
        if (user1 != null) {
            throw new RuntimeException("Email Allready Present !");
        } else {
            User user = new User();
            user.setName(req.name());
            user.setPassword(req.password());
            user.setUserId(UUID.randomUUID().toString());
            user.setUserName(req.userName());
            user.setEmail(req.email());
            userRepository.save(user);
            return user.getUserId();
        }
    }

    @Transactional
    public void addToolToAgent(String agentUuid, AddToolRequest req) {
        Agent agent = agentRepo.findByAgentId(agentUuid)
                .orElseThrow(() -> new NoSuchElementException("agent not found"));
        ToolEntity te = new ToolEntity();
        te.setAgent(agent);
        te.setName(req.name());
        te.setClassName(req.className());
        te.setConfigJson(req.configJson());
        agent.getTools().add(te);
        agentRepo.save(agent);
    }


    public String runAgent(String uuid, String input, Long userId) {
        System.out.println("runAgent started with uuid=" + uuid + ", input=" + input + ", userId=" + userId);

        Agent agentEntity = agentRepo.findByAgentId(uuid)
                .orElseThrow(() -> {
                    System.out.println("Agent not found with uuid: " + uuid);
                    return new NoSuchElementException("agent not found");
                });
        System.out.println("Found AgentEntity: " + agentEntity);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    System.out.println("User not found with id: " + userId);
                    return new RoutingError("Invalid User Id Passed !");
                });
        System.out.println("Found User: " + user);

        List<Tool> tools = toolRegistry.instantiateTools(agentEntity.getTools());
        System.out.println("Instantiated tools: " + tools);

        BaseAgent agent = agentFactory.createAgentInstance(agentEntity);
        System.out.println("Created BaseAgent instance: " + agent);

        String appName = String.format("%s::%s", agentEntity.getName(), user.getId());
        Runner runner = new Runner(agent, appName, new InMemoryArtifactService(), new InMemorySessionService());
        // Get or create cached runner for this agent
        UserSessions userSessions = getSessionByUserAndAgentId(user, agentEntity,  runner);
        Session session = userSessions.getSession();
        System.out.println("Obtained session: " + session);
        System.out.println("Session id: " + session.id());
        System.out.println("User id: " + user.getUserId());
        StringBuilder pastConversation = new StringBuilder();
        pastConversation.append("This is my new input for this session : ").append(input);
        Content userMsg = Content.fromParts(Part.fromText(pastConversation.toString()));
        System.out.println("Created Content from input: " + userMsg);
        StringBuilder responseBuilder = new StringBuilder();
        System.out.println("Starting runner.runAsync...");
        List<agents.helpers.Event> eventArrayList = new ArrayList<>();
        try {
            Flowable<Event> events = runner.runWithSessionId(user.getUserId(), session.id(), userMsg, RunConfig.builder().build());
            events.blockingForEach(event -> {
                agents.helpers.Event event1 = new agents.helpers.Event();
                event1.setInvocationId(event.invocationId());
                event1.setEventId(event.id());
                event1.setInput(input);
                eventArrayList.add(event1);
                System.out.println("Received event: " + event);
                responseBuilder.append(event.stringifyContent());
            });
        } catch (Exception e) {
            System.out.println("Exception while running agent: " + e.getMessage());
            e.printStackTrace();
            throw new RoutingError(e.getMessage());
        }

        String response = responseBuilder.toString();
        Conversation conversation = new Conversation();
        String id = UUID.randomUUID().toString();
        conversation.setId(id);
        conversation.setInput(input);
        conversation.setResponse(response);
        List<Event> e = session.events();
        System.out.println("Code reaches here ");
        session.events().removeAll(e);
        userSessions.setSession(session);
        userSessions.getHistory().add(conversation);
        userSessions.getConfig().getEventList().addAll(eventArrayList);
        userSessionRepository.save(userSessions);

        System.out.println("Completed runAgent, response: " + response);

        return response;
    }

    private UserSessions getSessionByUserAndAgentId(User user, Agent entity,  Runner runner) {
        System.out.println("Fetching UserSessions for userId=" + user.getId() + ", agentId=" + entity.getId());

        String appName = String.format("%s::%s", entity.getName(), user.getId());
        System.out.println("App name: " + appName);

        UserSessions userSessions = userSessionRepository.findByUserIdAndAgentIdAndActive(user.getId(), entity.getId(), "true");
        Session session = null;

        if (userSessions == null) {
            System.out.println("No existing UserSessions found, creating new session and UserSessions.");


            System.out.println("sessions : " + runner.sessionService());
            session = createNewSession(runner, appName, user.getUserId());

            UserSessions newUserSession = new UserSessions();
            newUserSession.setAgent(entity);
            newUserSession.setUser(user);
            //newUserSession.setRunner(runner);
            newUserSession.setSessionId(session.id());
            newUserSession.setSession(session);
            newUserSession.setLastUpdateTime(Instant.now());
            newUserSession.setAppName(appName);

            userSessionRepository.save(newUserSession);
            System.out.println("Saved new UserSessions: " + newUserSession);
            userSessions = newUserSession;
        } else {
            InMemorySessionService inMemorySessionService = (InMemorySessionService) runner.getSessionService();
            inMemorySessionService.addSession(userSessions.getSession(), userSessions.getSessionId(), user.getUserId(), appName);
            for (agents.helpers.Event event : userSessions.getConfig().getEventList()) {
                inMemorySessionService.appendEvent(userSessions.getSession(), Event.builder()
                        .id(event.getEventId())
                        .invocationId(event.getInvocationId())
                        .author("user")
                        .content((Content.fromParts(Part.fromText(event.getInput())))).build());
            }
            System.out.println("Events : " + inMemorySessionService.listEvents(appName, user.getUserId(), userSessions.getSessionId()));

        }
        return userSessions;
    }


    private Session createNewSession(Runner runner, String appName, String userId) {
        try {
            Session session = runner.sessionService()
                    .createSession(appName, userId)
                    .blockingGet();

            if (session == null) {
                throw new RuntimeException("Failed to create session - returned null");
            }

            System.out.println("Successfully created new session: " + session.id());

            Session verificationSession = runner.sessionService()
                    .getSession(appName, userId, session.id(), Optional.empty())
                    .blockingGet();

            if (verificationSession == null) {
                System.out.println("Warning: Session verification failed, but proceeding with original session");
            } else {
                System.out.println("Session verification successful: " + verificationSession.id());
            }

            return session;
        } catch (Exception e) {
            System.out.println("Error creating session: " + e.getMessage());
            throw new RuntimeException("Failed to create session", e);
        }
    }





}