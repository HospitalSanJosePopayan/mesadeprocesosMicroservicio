package husjp.api.mesaprocesos.service;

import java.util.List;

import husjp.api.mesaprocesos.service.dto.ProcesoDTO;

public interface IProcesoService {
	
	List<ProcesoDTO> obtenerProcesos();
	List<ProcesoDTO>obtenerProcesosArea(Integer id);
    ProcesoDTO crearProceso(ProcesoDTO procesoDTO);
    ProcesoDTO actualizarProceso(Integer id, ProcesoDTO procesoDTO);
}
