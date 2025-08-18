package agents.entity;

import agents.helpers.Conversation;
import agents.helpers.UserSessionConfig;
import agents.service.*;
import com.google.adk.sessions.Session;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Instant;
import java.util.ArrayList;
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

    @Convert(converter = SessionConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT")
    private Session session;


    @OneToOne
    @JoinColumn(name = "agent_id")
    private AgentEntity agent;

    public List<Conversation> getHistory() {
        if(history == null) {
            this.history = new ArrayList<>();
        }
        return history;
    }

    @Column(columnDefinition = "TEXT")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Conversation> history = new ArrayList<>();

    private String active = "true";

    public UserSessionConfig getConfig() {
        if(this.config == null) {
            this.config = new UserSessionConfig();
        }
        return config;
    }

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT")
    private UserSessionConfig config = new UserSessionConfig();

}