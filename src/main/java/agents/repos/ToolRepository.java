package agents.repos;

import agents.entity.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<ToolEntity, Long> {
   // List<ToolEntity> findByAgentUuid(String agentUuid);
}
