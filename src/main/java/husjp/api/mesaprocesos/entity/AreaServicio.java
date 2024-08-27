package husjp.api.mesaprocesos.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "AreaServicio")
public class AreaServicio {
    @Id
    private Integer idarea;
    private String nombre;
    private String tipo;
    @ManyToMany(mappedBy = "areaServicios")
    private List<Usuario> usuarios;

}
