package agents.dto;

import agents.helpers.enums.AgentType;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record CreateAgentRequest (
        @NotNull String name,
        @NotNull String description,
        @NotNull String model,
        @NotNull String instruction ,
        AgentType type ,
        Set<Long> agents ) { }
