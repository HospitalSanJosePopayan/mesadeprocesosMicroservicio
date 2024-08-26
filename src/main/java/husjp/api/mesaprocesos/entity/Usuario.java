package husjp.api.mesaprocesos.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name ="idPersona")
public class Usuario  extends Persona {

    public Usuario (String documento, String nombreCompleto){
        super(documento, nombreCompleto);
    }

    private String password;
    private Boolean estado;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "idPersona"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idarea")
    private AreaServicio areaServicioUsuario;

}
