package agents.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
public class ToolEntity extends AttrsModel {

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    private String name;

    private String className;

    @Column(columnDefinition = "TEXT")
    private String configJson;

}
