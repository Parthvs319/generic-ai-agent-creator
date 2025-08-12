package agents.entity;

import agents.annotations.DbJsonB;
import agents.service.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.adk.agents.BaseAgent;
import com.google.adk.artifacts.BaseArtifactService;
import com.google.adk.sessions.BaseSessionService;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "runner")
@NoArgsConstructor
public class Runner extends AttrsModel {

    @Convert(converter = InMemoryRunnerConverter.class)
    @Column(columnDefinition = "TEXT")
    private InMemoryRunner runner;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String appName;

    @OneToOne
    @JoinColumn(name = "agent_id")
    private AgentEntity agent;

}
