package agents.entity.repos;

import agents.entity.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunnerRepo extends JpaRepository<Runner, Long> {

    Runner findByUserIdAndAppNameAndAgentId(Long userId, String appName , Long agentId);


}
