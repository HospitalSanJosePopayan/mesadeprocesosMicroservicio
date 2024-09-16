package husjp.api.mesaprocesos.service;

import java.util.List;

import husjp.api.mesaprocesos.entity.AreaServicio;
import husjp.api.mesaprocesos.service.dto.AreaServicioDTO;
import husjp.api.mesaprocesos.service.dto.UsuarioDTO;

public interface IAreaServicioService {
    List<AreaServicioDTO> obtenerAreasServicio();
    List <UsuarioDTO>buscarUsuarioporArea(Integer id);
    
}
