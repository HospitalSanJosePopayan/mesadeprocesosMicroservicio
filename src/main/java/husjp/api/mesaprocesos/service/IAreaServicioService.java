package husjp.api.mesaprocesos.service;

import java.util.List;

import husjp.api.mesaprocesos.service.dto.ServicioDTO;
import husjp.api.mesaprocesos.service.dto.UsuarioDTO;

public interface IAreaServicioService {
    List<ServicioDTO> obtenerAreasServicio();
    List <UsuarioDTO>buscarUsuarioporArea(Integer id);
    
}
