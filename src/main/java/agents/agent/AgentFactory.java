package agents.agent;

import agents.entity.AgentEntity;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.GoogleSearchTool;
import org.springframework.stereotype.Component;

/**
 * Builds ADK agents dynamically from db entities.
 * Note: ADK SDK method names may differ — adapt accordingly.
 */
@Component
public class AgentFactory {

    public BaseAgent createAgentInstance(AgentEntity entity) {
        LlmAgent.Builder builder = LlmAgent.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .model(entity.getModel())
                .instruction(entity.getInstruction())
                .tools(new GoogleSearchTool());

        return builder.build();
    }
}
