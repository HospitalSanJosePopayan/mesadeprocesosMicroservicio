package husjp.api.mesaprocesos.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="proceso")
public class Proceso {
    @Id
    private Integer idproceso;
    private String Nombre;
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proceso")
    private List<SubProceso> subprocesos;

     @ManyToOne
     @JoinColumn(name = "idArea", nullable = false)
     private  AreaServicio idarea;

}