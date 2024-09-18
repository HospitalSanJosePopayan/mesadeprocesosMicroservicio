package husjp.api.mesaprocesos.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="proceso")
public class Proceso {
    @Id
    @Column(name="id_proceso")
    private Integer id;
    private String nombre;
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proceso")
    private List<SubProceso> subprocesos;

     @ManyToOne
     @JoinColumn(name = "idArea", nullable = false)
     private  AreaServicio idarea;

}