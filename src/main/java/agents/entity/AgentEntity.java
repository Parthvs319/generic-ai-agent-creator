package agents.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "agents")
@NoArgsConstructor
public class AgentEntity extends AttrsModel{

    @Column(nullable=false, unique=true, length=36)
    private String agentId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String model;

    @Column(columnDefinition = "TEXT")
    private String instruction;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ToolEntity> tools = new ArrayList<>();
}
