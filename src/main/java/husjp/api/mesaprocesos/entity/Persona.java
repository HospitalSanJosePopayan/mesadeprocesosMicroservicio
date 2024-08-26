package husjp.api.mesaprocesos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersona;
    @Column(unique = true)
    private String documento;
    private String nombreCompleto;

    public Persona (String documento, String nombreCompleto){
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
    }

}
