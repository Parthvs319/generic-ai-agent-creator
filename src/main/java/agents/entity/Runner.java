package agents.entity;

import agents.service.*;
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
    private Agent agent;

}
