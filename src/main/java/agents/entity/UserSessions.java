package agents.entity;

import agents.service.Runner;
import com.google.adk.events.Event;
import com.google.adk.sessions.Session;
import com.google.adk.sessions.State;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_sessions")
public class UserSessions {

    @Id
    private String id;

    private String appName;

    private Runner runner;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String sessionId;

    private Instant lastUpdateTime;

    private State state;

    private Session session;

    private List<Event> events;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AgentEntity agent;
}