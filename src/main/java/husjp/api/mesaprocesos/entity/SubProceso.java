package husjp.api.mesaprocesos.entity;


import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subproceso")
public class SubProceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSubProceso;
    private String NombreSubproceso;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "idproceso")
    private Proceso proceso;

    @OneToMany(mappedBy = "subProceso")
    private List<UsuarioProceso> usuarioProcesos;


}
