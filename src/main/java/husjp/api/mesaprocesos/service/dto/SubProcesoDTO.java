package husjp.api.mesaprocesos.service.dto;

import lombok.Data;

@Data
public class SubProcesoDTO {
	private Integer idSubProceso;
 	private String nombreSubproceso;
 	private String descripcion;
	private Integer idproceso;

}
