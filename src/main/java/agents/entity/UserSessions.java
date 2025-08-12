package agents.entity;

import agents.annotations.DbJsonB;
import agents.service.*;
import agents.service.Runner;
import com.google.adk.events.Event;
import com.google.adk.sessions.Session;
import com.google.adk.sessions.State;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "user_sessions")
public class UserSessions extends AttrsModel {

    private String appName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String sessionId;

    private Instant lastUpdateTime;

    @Convert(converter = StateConverter.class)
    @Column(columnDefinition = "TEXT")
    private State state;

    @Convert(converter = SessionConverter.class)
    @Column(columnDefinition = "TEXT")
    private Session session;

    @Convert(converter = EventListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Event> events;

    @OneToOne
    @JoinColumn(name = "agent_id")
    private AgentEntity agent;
}