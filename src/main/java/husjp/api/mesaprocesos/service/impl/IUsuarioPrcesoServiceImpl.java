package husjp.api.mesaprocesos.service.impl;

import java.lang.Error;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import husjp.api.mesaprocesos.entity.Proceso;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.*;
import husjp.api.mesaprocesos.repository.ProcesoRepository;
import husjp.api.mesaprocesos.service.dto.UsuarioProcesobodyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import husjp.api.mesaprocesos.entity.SubProceso;
import husjp.api.mesaprocesos.entity.Usuario;
import husjp.api.mesaprocesos.entity.UsuarioProceso;
import husjp.api.mesaprocesos.repository.SubProcesoRepository;
import husjp.api.mesaprocesos.repository.UsuarioProcesoRepository;
import husjp.api.mesaprocesos.repository.UsuarioRepository;
import husjp.api.mesaprocesos.service.IUsuarioProcesoService;
import husjp.api.mesaprocesos.service.dto.UsuarioProcesoDTO;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class IUsuarioPrcesoServiceImpl  implements IUsuarioProcesoService   {
	private UsuarioProcesoRepository usuarioProcesoRepository;
    private SubProcesoRepository subProcesoRepository;
    private ProcesoRepository procesoRepository;
    private UsuarioRepository usuarioRespository;
    private ModelMapper modelMapper;
    @Override
    public List<UsuarioProcesoDTO> obtenerUsuariosprocesos() {
        return usuarioProcesoRepository.findAll().stream()
                .map(usuarioProceso -> {
                    UsuarioProcesoDTO dto = new UsuarioProcesoDTO();
                    Usuario usuario = usuarioProceso.getUsuario();
                    if (usuario == null || usuario.getDocumento() == null || usuario.getDocumento().isEmpty()) {
                        throw new IllegalArgumentException("El Usuario o su documento es nulo o vacío en el proceso con ID: " + usuarioProceso.getId());
                    }
                    SubProceso subProceso = usuarioProceso.getSubProceso();
                    if (subProceso == null || subProceso.getId() == null) {
                        throw new IllegalArgumentException("El SubProceso o su ID es nulo en el proceso con ID: " + usuarioProceso.getId());
                    }
                    dto.setId(usuarioProceso.getId());
                    dto.setEstado(usuarioProceso.getEstado());
                    dto.setFechaInicio(usuarioProceso.getFechaInicio());
                    dto.setFechaFin(usuarioProceso.getFechaFin());
                    dto.setEnlace(usuarioProceso.getEnlace());
                    dto.setIdusuario(usuario.getDocumento());
                    dto.setIdsubProceso(subProceso.getId());
                    dto.setDescripcionSubproceso(subProceso.getDescripcion());
                    dto.setIdProceso(usuarioProceso.getSubProceso().getProceso().getId());
                    dto.setNombreUsuario(usuarioProceso.getUsuario().getNombreCompleto());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<UsuarioProcesoDTO> obtenerprocesosPorUsuario(String documentoUsuario) {
        Optional<Usuario> usuario = usuarioRespository.findByDocumento(documentoUsuario);
        if(usuario.isPresent()) {
            List<UsuarioProceso> usuarioProcesos = usuarioProcesoRepository.findAllByUsuarioDocumento(documentoUsuario);
            if(usuarioProcesos.isEmpty()) {
                throw new EntidadSinAsignaciones("El Usuario no tiene  subprocesos agendados ");
            }
            return usuarioProcesos.stream().map(up -> {
                UsuarioProcesoDTO dto = new UsuarioProcesoDTO();
                dto.setId(up.getId());
                dto.setEstado(up.getEstado());
                dto.setFechaInicio(up.getFechaInicio());
                dto.setFechaFin(up.getFechaFin());
                dto.setIdusuario(up.getUsuario().getDocumento());  // Este será el documento del usuario
                dto.setIdsubProceso(up.getSubProceso().getId());
                dto.setIdProceso(up.getSubProceso().getProceso().getId());
                dto.setDescripcionSubproceso(up.getSubProceso().getDescripcion());
                dto.setNombreUsuario(up.getUsuario().getNombreCompleto());
                dto.setEnlace(up.getEnlace());
                return dto;
            }).collect(Collectors.toList());
        }
        throw new EntidadNoExisteException("El Usuario No se encuentra");

    }
    @Override
    public UsuarioProcesoDTO crearUsuarioProceso(UsuarioProcesobodyDTO usuarioProcesobodyDTO) {
        Optional<Proceso> procesoOpt = procesoRepository.findById(usuarioProcesobodyDTO.getIdProceso());
        if(!procesoOpt.isPresent()){
            throw  new EntidadNoExisteException("No se encontro el Proceso ");
        }
        Optional<SubProceso> subProcesoOpt = subProcesoRepository.findById(usuarioProcesobodyDTO.getIdsubProceso());
        if (!subProcesoOpt.isPresent()) {
            throw new EntidadNoExisteException("No se encontro el subproceso");
        }
        // Buscar el usuario por su documento en lugar de por ID
        Optional<Usuario> usuarioOpt = usuarioRespository.findByDocumento(usuarioProcesobodyDTO.getDocumento());
        if (!usuarioOpt.isPresent()) {
            throw new EntidadNoExisteException("No se encontro el Usuario");
        }
        Optional<UsuarioProceso> existenteUsuarioProceso = usuarioProcesoRepository
                .findUsuarioProcesoEnCurso(
                        usuarioOpt.get().getIdPersona(),
                        subProcesoOpt.get().getId()
                );
        if (existenteUsuarioProceso.isPresent())
        {
            if(existenteUsuarioProceso.get().getEstado()==1){
                throw new OperacionNoPermitida("El usuario ya tiene este proceso en curso.");
            }
            if(existenteUsuarioProceso.get().getEstado()==3) {
                throw new OperacionNoPermitida("El usuario tiene este mismo  proceo Atrasado.");
            }
        }
        UsuarioProceso usuarioSubProceso = modelMapper.map(usuarioProcesobodyDTO, UsuarioProceso.class);
        // Asignar entidades existentes
        LocalDate FechaInicio = usuarioSubProceso.getFechaInicio().toLocalDate();
        LocalDate FechaFin = usuarioSubProceso.getFechaFin().toLocalDate();
        if(usuarioSubProceso.getFechaInicio().isBefore(LocalDateTime.now()) || usuarioSubProceso.getFechaFin().isBefore(usuarioSubProceso.getFechaInicio()) || FechaInicio.isEqual(FechaFin)){
            throw new FechaFueraRango("Las fechas estan fuera de rango");
        }
        usuarioSubProceso.setSubProceso(subProcesoOpt.get());
        usuarioSubProceso.setUsuario(usuarioOpt.get());;
        usuarioSubProceso.setEstado(1);
        UsuarioProceso savedUsuarioSubProceso = usuarioProcesoRepository.save(usuarioSubProceso);
        UsuarioProcesoDTO usuarioProcesoDTO = new UsuarioProcesoDTO();
        usuarioProcesoDTO.setId(savedUsuarioSubProceso.getId());
        usuarioProcesoDTO.setEstado(savedUsuarioSubProceso.getEstado());
        usuarioProcesoDTO.setFechaInicio(savedUsuarioSubProceso.getFechaInicio());
        usuarioProcesoDTO.setFechaFin(savedUsuarioSubProceso.getFechaFin());
        usuarioProcesoDTO.setIdusuario(savedUsuarioSubProceso.getUsuario().getDocumento());
        usuarioProcesoDTO.setIdsubProceso(savedUsuarioSubProceso.getSubProceso().getId());
        usuarioProcesoDTO.setIdProceso(savedUsuarioSubProceso.getSubProceso().getProceso().getId());
        usuarioProcesoDTO.setDescripcionSubproceso(savedUsuarioSubProceso.getSubProceso().getDescripcion());
        usuarioProcesoDTO.setNombreUsuario(savedUsuarioSubProceso.getUsuario().getNombreCompleto());
        return usuarioProcesoDTO;
    }
    @Override
    public UsuarioProcesoDTO actualizarUsuarioProcesoFecha(Integer id, LocalDateTime nuevaFechaFin) {
        Optional<UsuarioProceso> usuOptional = usuarioProcesoRepository.findById(id);
        if (usuOptional.isPresent()) {
            if(nuevaFechaFin.isBefore(LocalDateTime.now())){
                throw  new OperacionNoPermitida("la fecha no puede ser menor a la fecha actual  ");
            }
            UsuarioProceso usuarioProceso = usuOptional.get();
            // Actualizar la fecha fin
            usuarioProceso.setFechaFin(nuevaFechaFin);
            // Si la nueva fecha fin es superior a la fecha actual, cambiar el estado a 1 (en curso)
            if (nuevaFechaFin.isAfter(LocalDateTime.now())) {
                usuarioProceso.setEstado(1);
            } else {
                // Si la nueva fecha fin no es superior a la fecha actual, mantén el estado como atrasado (3)
                usuarioProceso.setEstado(3);
            }
            UsuarioProceso savedUsuarioProceso = usuarioProcesoRepository.save(usuarioProceso);
            // Convertir la entidad guardada en DTO y devolverla
            return modelMapper.map(savedUsuarioProceso, UsuarioProcesoDTO.class);
        } else {
            throw new EntidadNoExisteException("No se encontro el usuarioproceso");
        }
    }

    @Override
    public UsuarioProcesoDTO actualizarUsuarioprocesoEstado(Integer idsubproceso, String enlace) {
        Optional<UsuarioProceso> usuOptional = usuarioProcesoRepository.findById(idsubproceso);
        if(usuOptional.isPresent()){
            UsuarioProceso usuarioProceso = usuOptional.get();
            usuarioProceso.setEstado(2);
            usuarioProceso.setEnlace(enlace);
            UsuarioProceso savedUsuarioProceso = usuarioProcesoRepository.save(usuarioProceso);
            return modelMapper.map(savedUsuarioProceso, UsuarioProcesoDTO.class);
        }else {
            throw new EntidadNoExisteException("No se encontro el usuarioproceso ");
        }
    }
    @Scheduled(cron = "0 0 0 6 * ?")
   public void actualizarEstadosAutomáticamente() {
        List<UsuarioProceso> procesos = usuarioProcesoRepository.findAll();
        System.out.println("la fecha es "+ LocalDateTime.now());
        procesos.forEach(proceso -> {
            if (proceso.getEstado() == 1 && proceso.getFechaFin().isBefore(LocalDateTime.now())) {
                proceso.setEstado(3); // Estado "atrasado"
                usuarioProcesoRepository.save(proceso);
            }
        });
    }
    @Scheduled(cron= "0 0 0 22 * ?")
    public  void ElimiarUsuariosProcesosMensuales(){
      LocalDateTime hoy = LocalDateTime.now();
      List<Integer> estados = List.of(2,3);
      List<UsuarioProceso>usuarioProcesosEliminar= usuarioProcesoRepository.findByEstadoAndFechaFin(estados,hoy);
      if(!usuarioProcesosEliminar.isEmpty()){
          System.out.println("Se Eliminaron Usuarios procesos ");
          usuarioProcesosEliminar.forEach(usuarioProceso -> {
              System.out.println("se elimino la asignacion "+usuarioProceso.getUsuario()+"con id "+ usuarioProceso.getId());
          });
          usuarioProcesoRepository.deleteAll(usuarioProcesosEliminar);
      }
    }
    @Override
    public void eliminarUsuarioProceso(Integer id) {
       Optional <UsuarioProceso> usuOptional = usuarioProcesoRepository.findById(id);
       if(usuOptional.isPresent()){
         usuarioProcesoRepository.deleteById(id);
       }else{
        throw new  EntidadNoExisteException ("UsuarioProceso no encontrado.");
       }
    }
    @Override
    public List<UsuarioProcesoDTO> obtenerUsuarioProcesoArea(Integer idArea) {
        if (idArea == null || idArea <= 0) {
            throw new Error("El id del área no puede ser nulo o negativo");
        }
        List<UsuarioProceso> usuarioProcesos = usuarioProcesoRepository.usuariosPorArea(idArea);
        if (usuarioProcesos == null || usuarioProcesos.isEmpty()) {
            throw new EntidadSinAsignaciones("El área seleccionada no tiene asignaciones de procesos");
        }
        List<UsuarioProcesoDTO> responseusuariosprocesos = new ArrayList<>();
        Set<Integer> idUsuarioProcesoUnicos = new HashSet<>();

        for (UsuarioProceso usuarioProceso : usuarioProcesos) {
            Integer idUsuarioProceso = usuarioProceso.getId();
            if (!idUsuarioProcesoUnicos.contains(idUsuarioProceso)) {
                idUsuarioProcesoUnicos.add(idUsuarioProceso);
                UsuarioProcesoDTO usuarioProcesoDTO = new UsuarioProcesoDTO();
                usuarioProcesoDTO.setIdusuario(usuarioProceso.getUsuario().getDocumento());
                usuarioProcesoDTO.setIdsubProceso(usuarioProceso.getSubProceso().getId());
                usuarioProcesoDTO.setNombreUsuario(usuarioProceso.getUsuario().getNombreCompleto());
                usuarioProcesoDTO.setId(idUsuarioProceso);
                usuarioProcesoDTO.setIdProceso(usuarioProceso.getSubProceso().getProceso().getId());
                usuarioProcesoDTO.setDescripcionSubproceso(usuarioProceso.getSubProceso().getDescripcion());
                usuarioProcesoDTO.setFechaInicio(usuarioProceso.getFechaInicio());
                usuarioProcesoDTO.setFechaFin(usuarioProceso.getFechaFin());
                usuarioProcesoDTO.setEstado(usuarioProceso.getEstado());
                responseusuariosprocesos.add(usuarioProcesoDTO);
            }
        }
        return responseusuariosprocesos;
    }

    @Override
    public UsuarioProcesoDTO transferirSubprocesoAUsuario(Integer idUsuarioProceso, String nuevoUsuarioId) {
        Optional<UsuarioProceso> usuarioProcesoOpt = usuarioProcesoRepository.findById(idUsuarioProceso);
        if (!usuarioProcesoOpt.isPresent()) {
            throw  new EntidadNoExisteException ("UsuarioProceso no encontrado.");
        }
        Optional<Usuario> nuevoUsuarioOpt = usuarioRespository.findByDocumento(nuevoUsuarioId);
        if (!nuevoUsuarioOpt.isPresent()) {
            throw new EntidadNoExisteException("Usuario No Encontrado");
        }
        Optional<UsuarioProceso> existenteUsuarioProceso = usuarioProcesoRepository.findUsuarioProcesoEnCurso(nuevoUsuarioOpt.get().getIdPersona(),usuarioProcesoOpt.get().getSubProceso().getId());
        if(existenteUsuarioProceso.isPresent()){
            throw  new OperacionNoPermitida("El Usuario ya tiene este proceso en curso ");
        }
        // Actualizar el UsuarioProceso con el nuevo usuario
        UsuarioProceso usuarioProceso = usuarioProcesoOpt.get();
        usuarioProceso.setUsuario(nuevoUsuarioOpt.get());
        // Guardar el cambio en la base de datos
        UsuarioProceso usuarioProcesoActualizado = usuarioProcesoRepository.save(usuarioProceso);
        // Convertir la entidad actualizada a DTO y devolverla
        UsuarioProcesoDTO usuarioProcesoDTO = new UsuarioProcesoDTO();
        usuarioProcesoDTO.setId(usuarioProcesoActualizado.getId());
        usuarioProcesoDTO.setEstado(usuarioProcesoActualizado.getEstado());
        usuarioProcesoDTO.setFechaInicio(usuarioProcesoActualizado.getFechaInicio());
        usuarioProcesoDTO.setFechaFin(usuarioProcesoActualizado.getFechaFin());
        usuarioProcesoDTO.setIdusuario(usuarioProcesoActualizado.getUsuario().getDocumento());
        usuarioProcesoDTO.setIdsubProceso(usuarioProcesoActualizado.getSubProceso().getId());
        usuarioProcesoDTO.setIdProceso(usuarioProcesoActualizado.getSubProceso().getProceso().getId());
        usuarioProcesoDTO.setDescripcionSubproceso(usuarioProcesoActualizado.getSubProceso().getDescripcion());
        usuarioProcesoDTO.setNombreUsuario(usuarioProcesoActualizado.getUsuario().getNombreCompleto());
        usuarioProcesoDTO.setEnlace(usuarioProcesoActualizado.getEnlace());
        return  usuarioProcesoDTO;
    }
}
