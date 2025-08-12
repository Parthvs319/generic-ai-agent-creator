package agents.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.adk.agents.BaseAgent;
import com.google.adk.artifacts.InMemoryArtifactService;
import com.google.adk.sessions.InMemorySessionService;
import com.google.adk.sessions.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/** The class for the in-memory GenAi runner, using in-memory artifact and session services. */



@EqualsAndHashCode(callSuper = true)
@Data
public class InMemoryRunner extends agents.service.Runner {

    private Session sessions ;
    
    private Runner runner;
    
    public InMemoryRunner(BaseAgent agent) {
        // TODO: Change the default appName to InMemoryRunner to align with adk python.
        // Check the dev UI in case we break something there.
        this(agent, /* appName= */ agent.name());
    }

    public InMemoryRunner(BaseAgent agent, String appName) {
        new Runner(agent, appName, new InMemoryArtifactService(), new InMemorySessionService());
    }


    public InMemoryRunner getRunner(BaseAgent agent, String appName) {
        InMemoryRunner runner1 = (InMemoryRunner) new Runner(agent, appName, new InMemoryArtifactService(), new InMemorySessionService());
        return runner1;
    }
}

