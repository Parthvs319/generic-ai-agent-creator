package agents.repos;

import agents.entity.UserSessions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSessions, String> {


    UserSessions findById(Long id);

    UserSessions findByUserIdAndAgentIdAndActive(Long userId, Long agentId , String active);

}