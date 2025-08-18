package agents.entity.repos;

import agents.entity.Agent;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByAgentId(String agentId);

    @NotNull Optional<Agent> findById(@NotNull Long id);
}
