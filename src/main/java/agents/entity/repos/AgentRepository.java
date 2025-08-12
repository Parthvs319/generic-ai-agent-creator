package agents.entity.repos;

import agents.entity.AgentEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
    Optional<AgentEntity> findByAgentId(String agentId);

    @NotNull Optional<AgentEntity> findById(@NotNull Long id);
}
