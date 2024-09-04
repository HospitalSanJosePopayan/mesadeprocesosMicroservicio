package husjp.api.mesaprocesos.service;
import java.time.LocalDateTime;
import java.util.List;

import husjp.api.mesaprocesos.service.dto.UsuarioProcesoDTO;
import husjp.api.mesaprocesos.service.dto.UsuarioProcesobodyDTO;

public interface IUsuarioProcesoService {
	
	 List<UsuarioProcesoDTO> obtenerUsuariosprocesos();
	   List<UsuarioProcesoDTO> obtenerprocesosPorUsuario(String documento);
	   UsuarioProcesoDTO crearUsuarioProceso(UsuarioProcesobodyDTO usuarioProcesobodyDTODTO);
	   UsuarioProcesoDTO actualizarUsuarioProcesoFecha(Integer id, LocalDateTime nuevaFechaFin);
	   UsuarioProcesoDTO actualizarUsuarioprocesoEstado(Integer idsubproceso, String enlace);
	   void eliminarUsuarioProceso(Integer id); 
	   public List<UsuarioProcesoDTO> obtenerUsuarioProcesoArea(Integer idArea);
	   public UsuarioProcesoDTO transferirSubprocesoAUsuario(Integer idUsuarioProceso, String nuevoUsuarioId);

}
