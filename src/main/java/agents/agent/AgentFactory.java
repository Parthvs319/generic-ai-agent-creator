package agents.agent;

import agents.entity.Agent;
import agents.entity.repos.AgentRepository;
import agents.helpers.enums.AgentType;
import agents.service.AgentService;
import com.google.adk.agents.*;
import com.google.adk.tools.GoogleSearchTool;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Builds ADK agents dynamically from db entities.
 * Note: ADK SDK method names may differ — adapt accordingly.
 */
@Component
public class AgentFactory {

    @Autowired
    private final AgentRepository agentRepo;

    public AgentFactory(AgentRepository agentRepo) {
        this.agentRepo = agentRepo;
    }

    public BaseAgent createAgentInstance(Agent entity) {
        BaseAgent agent = null;
        System.out.println("Entity Type : " + entity.getType());
        AgentType type = (AgentType) entity.getType();
        if(type.equals(AgentType.SINGLE_AGENT)) {
            agent = LlmAgent.builder()
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .model(entity.getModel())
                    .instruction(entity.getInstruction())
                    .tools(new GoogleSearchTool()).build();
        } else {
            List<BaseAgent> workflowAgentList = new ArrayList<>();

            for (Long id : entity.getAgents()) {
                Agent agentEntity = agentRepo.findById(id)
                        .orElseThrow(() -> {
                            System.out.println("Agent not found with id: " + id);
                            return new NoSuchElementException("agent not found");
                        });

                if(agentEntity != null) {
                    BaseAgent baseAgent =  LlmAgent.builder()
                            .name(agentEntity.getName())
                            .description(agentEntity.getDescription())
                            .model(agentEntity.getModel())
                            .instruction(agentEntity.getInstruction())
                            .tools(new GoogleSearchTool()).build();
                    workflowAgentList.add(baseAgent);
                }
            }


            if (type.equals(AgentType.PARALLEL)) {
                agent  = ParallelAgent.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .subAgents(workflowAgentList)
                        .build();
            } else if (type.equals(AgentType.SEQUENTIAL)) {
                agent = SequentialAgent.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .subAgents(workflowAgentList)
                        .build();
            } else if (type.equals(AgentType.LOOP_AGENT)) {
                agent = LoopAgent.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .subAgents(workflowAgentList)
                        .build();
            }
        }
       return agent;
    }
}
