package husjp.api.mesaprocesos.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaServicioDTO {
    private Integer idarea;
    private String Nombre;
    private String Tipo;
}
