package agents.entity.repos;

import agents.entity.AgentEntity;
import agents.entity.Runner;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RunnerRepo extends JpaRepository<Runner, Long> {

    Runner findByUserIdAndAppNameAndAgentId(Long userId, String appName , Long agentId);


}
