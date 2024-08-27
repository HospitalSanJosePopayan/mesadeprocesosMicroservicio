package husjp.api.mesaprocesos.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "AreaServicio")
public class AreaServicio {
    @Id
    private Integer idarea;
    private String nombre;
    private String tipo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaServicioUsuario")
    private List<Usuario> usuarios;

}
