package husjp.api.mesaprocesos.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcesoDTO {
	   private Integer idproceso;
	    private String Nombre;
	    private String descripcion;
	    private Integer idarea;
}
