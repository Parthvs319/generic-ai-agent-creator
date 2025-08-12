package agents.entity.repos;

import agents.entity.UserSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSessions, String> {

    Optional<UserSessions> findById(String id);

    UserSessions findByUserIdAndAgentId(Long userId, Long agentId);

}