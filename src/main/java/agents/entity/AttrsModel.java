package agents.entity;

import agents.annotations.DbJsonB;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AttrsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "updated_at")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());;

    @Column(name = "deleted")
    private boolean deleted = false;

    @DbJsonB
    private HashMap<String,String> attrs = new HashMap<>();


    public HashMap<String,String> getAttrs(){
        if(attrs==null)
            attrs = new HashMap<>();
        return attrs;
    }


}
