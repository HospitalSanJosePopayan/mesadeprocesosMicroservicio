package husjp.api.mesaprocesos.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
 @Data
 @Table(name = "UsuarioProceso")
public class UsuarioProceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String enlace;

    @ManyToOne
    @JoinColumn(name="usuarioId", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "subprocesoId", nullable = false)
    private SubProceso subProceso;


    
}
