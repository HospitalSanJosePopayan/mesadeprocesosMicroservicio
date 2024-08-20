package husjp.api.mesaprocesos.service;

import husjp.api.mesaprocesos.service.dto.UsuarioDTO;

public interface IUsuarioService {
	 	
	UsuarioDTO buscarPorDocumento(String documento);

}
