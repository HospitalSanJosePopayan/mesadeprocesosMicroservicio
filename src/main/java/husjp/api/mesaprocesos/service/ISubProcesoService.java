package husjp.api.mesaprocesos.service;

import java.util.List;

import husjp.api.mesaprocesos.service.dto.SubProcesoDTO;

public interface ISubProcesoService {
	    List<SubProcesoDTO>obtenerSubprocesos();
	    List<SubProcesoDTO>obtenerSubprocesosporProceso(Integer idproceso);
	    SubProcesoDTO crearSubProceso(SubProcesoDTO subProcesoDTO);
	    SubProcesoDTO actualizarSubproceso( Integer id, SubProcesoDTO subProcesoDTO );
}
