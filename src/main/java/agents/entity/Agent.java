package agents.entity;

import agents.helpers.enums.AgentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "agents")
@NoArgsConstructor
public class Agent extends AttrsModel{

    @Column(nullable=false, unique=true, length=36)
    private String agentId;

    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    private AgentType type = AgentType.SINGLE_AGENT;

    public Set<Long> getAgents() {
        if(this.agents == null) {
            this.agents = new LinkedHashSet<>();
        }
        return this.agents;
    }

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Long> agents = new LinkedHashSet<>();

    @Column(columnDefinition = "TEXT")
    private String description;

    private String model;

    @Column(columnDefinition = "TEXT")
    private String instruction;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ToolEntity> tools = new ArrayList<>();
}
