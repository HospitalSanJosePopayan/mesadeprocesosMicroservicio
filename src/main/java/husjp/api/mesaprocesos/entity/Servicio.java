package husjp.api.mesaprocesos.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer id;
    private String nombre;
    private String tipo;
    @ManyToMany(mappedBy = "areaServicios")
    private List<Usuario> usuarios;

}
