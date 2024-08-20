package husjp.api.mesaprocesos.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;

import husjp.api.mesaprocesos.entity.Usuario;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.repository.UsuarioRepository;
import husjp.api.mesaprocesos.service.IUsuarioService;
import husjp.api.mesaprocesos.service.dto.UsuarioDTO;

public class UsuarioServiceImpl implements IUsuarioService {
	private UsuarioRepository usuarioRepository;
	private ModelMapper modelMapper;

	@Override
	public UsuarioDTO buscarPorDocumento(String documento) {
		 Optional<Usuario> usuarioEntity = usuarioRepository.findById(documento);
	        if(usuarioEntity.isEmpty()) {
	            throw new EntidadNoExisteException("Documento "+documento + "No existe en la BD");
	        }
	        UsuarioDTO usuarioDTO = modelMapper.map(usuarioEntity.get(), UsuarioDTO.class);
	        usuarioDTO.setPassword(null);
	        return usuarioDTO;
	}

}
