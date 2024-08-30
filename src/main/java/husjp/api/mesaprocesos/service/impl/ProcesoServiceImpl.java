package husjp.api.mesaprocesos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import husjp.api.mesaprocesos.entity.AreaServicio;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadSinAsignaciones;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadYaExiste;
import husjp.api.mesaprocesos.repository.AreaServicioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import husjp.api.mesaprocesos.entity.Proceso;
import husjp.api.mesaprocesos.repository.ProcesoRepository;
import husjp.api.mesaprocesos.service.IProcesoService;
import husjp.api.mesaprocesos.service.dto.ProcesoDTO;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProcesoServiceImpl implements IProcesoService {
	private ProcesoRepository procesosRepository;
	private AreaServicioRepository areaServicioRepository;
	private final ModelMapper modelMapper;
	 @Override
	  public List<ProcesoDTO> obtenerProcesos() {
	    List<ProcesoDTO> procesoDTOs = procesosRepository.findAll().stream()
	        .map(proceso -> modelMapper.map(proceso, ProcesoDTO.class))
	        .collect(Collectors.toList());
		if(procesoDTOs.isEmpty()) {
		 throw  new EntidadNoExisteException("No existen Procesos Registrados ");
		}
	    return procesoDTOs;
	  }
	@Override
	public List<ProcesoDTO> obtenerProcesosArea(Integer idArea) {
	   List<Proceso> obtenerprocesos=  procesosRepository.findByIdArea(idArea);
	   if(obtenerprocesos.isEmpty()){
		   throw  new EntidadSinAsignaciones("No existen procesos asignados para esta Area");
	   }
	   return obtenerprocesos.stream()
	   .map(proceso -> modelMapper.map(proceso, ProcesoDTO.class))
	   .collect(Collectors.toList());
	}
	  @Override
	  public ProcesoDTO crearProceso(ProcesoDTO procesoDTO) {
		   Optional<Proceso> procesoopt= procesosRepository.findById(procesoDTO.getIdproceso());
		 if(procesoopt.isPresent()){
			 throw  new EntidadYaExiste("El Id del proceso ya se encuentra Registrado");
		 }
		 Optional<AreaServicio> areaServicioOpt= areaServicioRepository.findById(procesoDTO.getIdarea());

		 if(areaServicioOpt.isPresent()){
	      Proceso proceso = modelMapper.map(procesoDTO, Proceso.class);
	      Proceso savedProceso = procesosRepository.save(proceso);
	      ProcesoDTO savedProcesoDTO = modelMapper.map(savedProceso, ProcesoDTO.class);
	      savedProcesoDTO.setDescripcion(savedProceso.getIdarea().getNombre()); // Asignar el nombre del área al DTO
	      return savedProcesoDTO;
		 }
		 throw  new EntidadNoExisteException("El Area de Servicio No se encuentra Registrada");
	  }
	  @Override
	  public ProcesoDTO actualizarProceso(Integer id, ProcesoDTO procesoDTO) {
	      Optional<Proceso> optionalProceso = procesosRepository.findById(id);
	      if (optionalProceso.isPresent()) {
	          Proceso proceso = optionalProceso.get();
	          proceso.setNombre(procesoDTO.getNombre());
	          proceso.setDescripcion(procesoDTO.getDescripcion());
	          proceso.setIdarea(modelMapper.map(procesoDTO, Proceso.class).getIdarea());
	          Proceso updatedProceso = procesosRepository.save(proceso);
	          return modelMapper.map(updatedProceso, ProcesoDTO.class);
	      } else {
	          throw new  EntidadNoExisteException("No se encontro el proceso");
	      }
	  }
}
