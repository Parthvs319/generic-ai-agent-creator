package agents.entity.repos;

import agents.entity.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolRepository extends JpaRepository<ToolEntity, Long> {
   // List<ToolEntity> findByAgentUuid(String agentUuid);
}
