package husjp.api.mesaprocesos.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
@Entity
 @Data
 @Table(name = "usuario_proceso")
public class UsuarioProceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer estado;
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    private String enlace;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "subproceso_id", nullable = false)
    private SubProceso subProceso;

}
