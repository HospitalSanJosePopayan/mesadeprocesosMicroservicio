package husjp.api.mesaprocesos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ROLES")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String rol;

}
