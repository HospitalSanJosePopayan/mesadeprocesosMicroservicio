package husjp.api.mesaprocesos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import husjp.api.mesaprocesos.entity.Servicio;
import husjp.api.mesaprocesos.entity.Usuario;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadSinAsignaciones;
import husjp.api.mesaprocesos.service.dto.UsuarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import husjp.api.mesaprocesos.repository.AreaServicioRepository;
import husjp.api.mesaprocesos.service.IAreaServicioService;
import husjp.api.mesaprocesos.service.dto.ServicioDTO;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class AreaServicioService implements IAreaServicioService {

    private AreaServicioRepository areaServicioRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ServicioDTO> obtenerAreasServicio() {
      List<ServicioDTO> areaDtos = areaServicioRepository.findAll().stream()
        .map(area -> modelMapper.map(area, ServicioDTO.class))
        .collect(Collectors.toList());
      if (areaDtos.isEmpty()){
          throw  new EntidadNoExisteException("No existen Areas de Servicios Registradas");
      }
        return areaDtos;
    }
    @Override
    public List<UsuarioDTO> buscarUsuarioporArea(Integer idArea) {
        Optional<Servicio> areaOpt = areaServicioRepository.findById(idArea);
        if (areaOpt.isPresent()){
            if (!areaOpt.get().getUsuarios().isEmpty()){
                List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
                List<Usuario> usuarios = areaOpt.get().getUsuarios();
                for (Usuario usuario : usuarios) {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setDocumento(usuario.getDocumento());
                    usuarioDTO.setNombrecompleto(usuario.getNombreCompleto());
                    usuarioDTOs.add(usuarioDTO);
                }
                return usuarioDTOs;
            }
            throw new EntidadSinAsignaciones("El Area no tiene Usuarios Asociados");
        }
        throw  new EntidadNoExisteException("No Existe la entidad ");

    }





}

