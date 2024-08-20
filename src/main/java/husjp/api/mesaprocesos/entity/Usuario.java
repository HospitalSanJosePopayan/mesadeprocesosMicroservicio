package husjp.api.mesaprocesos.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario  {

    @Id
    private String documento;
    private String password;
    private Boolean estado;
    private String nombrecompleto;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_documento"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idarea")
    private AreaServicio areaServicioUsuario;

}
