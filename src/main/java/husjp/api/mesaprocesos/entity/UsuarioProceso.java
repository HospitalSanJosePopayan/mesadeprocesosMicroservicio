package husjp.api.mesaprocesos.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
@Entity
 @Data
 @Table(name = "UsuarioProceso")
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
    @JoinColumn(name="usuarioId", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "subprocesoId", nullable = false)
    private SubProceso subProceso;


    
}
