package agents.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "user")
public class User extends AttrsModel{

    @Column(nullable=false, unique=true, length=36)
    private String name;

    @Column(nullable=false, unique=true, length=36)
    private String userId; // UUID

    @Column(nullable=false, unique=true, length=36)
    private String email;

    @Column(nullable=false, unique=true, length=36)
    private String userName;

    @Column(nullable=false, unique=true, length=36)
    private String password;

}
