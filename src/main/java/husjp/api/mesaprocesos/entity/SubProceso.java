package husjp.api.mesaprocesos.entity;


import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mp_subproceso")
public class SubProceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sub_proceso")
    private Integer id;
    @Column(name = "nombre_subproceso")
    private String nombreSubproceso;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "idproceso")
    private Proceso proceso;

    @OneToMany(mappedBy = "subProceso")
    private List<UsuarioProceso> usuarioProcesos;


}
