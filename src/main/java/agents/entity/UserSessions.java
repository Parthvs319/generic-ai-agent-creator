package agents.entity;

import agents.annotations.DbJsonB;
import agents.helpers.Conversation;
import agents.helpers.UserSessionConfig;
import agents.service.*;
import agents.service.Runner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.adk.events.Event;
import com.google.adk.sessions.Session;
import com.google.adk.sessions.State;
import com.mysql.cj.MysqlType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.dialect.MySQLEnumJdbcType;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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