package husjp.api.mesaprocesos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import husjp.api.mesaprocesos.entity.Servicio;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadSinAsignaciones;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadYaExiste;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.OperacionNoPermitida;
import husjp.api.mesaprocesos.repository.ServicioRepository;
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
	private ServicioRepository areaServicioRepository;
	private final ModelMapper modelMapper;
	 @Override
	  public List<ProcesoDTO> obtenerProcesos() {
		 List<Proceso> procesos = procesosRepository.findAll();
		 if (procesos.isEmpty()) {
			 throw new EntidadNoExisteException("No existen Procesos Registrados");
		 }
		 List<ProcesoDTO> procesoDTOs = procesos.stream()
				 .map(proceso -> {
					 ProcesoDTO procesoDTO = new ProcesoDTO();
					 procesoDTO.setId(proceso.getId());
					 procesoDTO.setNombre(proceso.getNombre());
					 procesoDTO.setDescripcion(proceso.getDescripcion());
					 procesoDTO.setId_servicio(proceso.getId_servicio().getId());
					 return procesoDTO;
				 })
				 .collect(Collectors.toList());

		 return procesoDTOs;
	  }
	@Override
	public List<ProcesoDTO> obtenerProcesosArea(Integer idArea) {
		List<Proceso> obtenerprocesos = procesosRepository.findByIdArea(idArea);
		if (obtenerprocesos.isEmpty()) {
			throw new EntidadSinAsignaciones("No existen procesos asignados para esta Area");
		}
		return obtenerprocesos.stream()
				.map(proceso -> {
					ProcesoDTO procesoDTO = new ProcesoDTO();
					procesoDTO.setId(proceso.getId());
					procesoDTO.setNombre(proceso.getNombre());
					procesoDTO.setDescripcion(proceso.getDescripcion());
					procesoDTO.setId_servicio(proceso.getId_servicio().getId());
					return procesoDTO;
				})
				.collect(Collectors.toList());
	}
	@Override
	public ProcesoDTO crearProceso(ProcesoDTO procesoDTO) {
		 if(procesoDTO.getId()==0){
			 throw new OperacionNoPermitida("La Id del proceso no puede ser cero");
		 }
		Optional<Proceso> procesoopt = procesosRepository.findById(procesoDTO.getId());
		if (procesoopt.isPresent()) {
			throw new EntidadYaExiste("El Id del proceso ya se encuentra Registrado");
		}
		 boolean existeNombre=procesosRepository.existsByNombreProceso(procesoDTO.getNombre(),procesoDTO.getId_servicio());
		 if(existeNombre){
			 throw  new EntidadYaExiste("El Nombre de este proceso ya se encuentra registrado ");
		 }

		Optional<Servicio> areaServicioOpt = areaServicioRepository.findById(procesoDTO.getId_servicio());
		if (areaServicioOpt.isPresent()) {
			Proceso proceso = new Proceso();
			proceso.setId(procesoDTO.getId());
			proceso.setNombre(procesoDTO.getNombre());
			proceso.setDescripcion(procesoDTO.getDescripcion());
			proceso.setId_servicio(areaServicioOpt.get());
			Proceso savedProceso = procesosRepository.save(proceso);
			ProcesoDTO savedProcesoDTO = new ProcesoDTO();
			savedProcesoDTO.setId(savedProceso.getId());
			savedProcesoDTO.setNombre(savedProceso.getNombre());
			savedProcesoDTO.setDescripcion(savedProceso.getDescripcion());
			savedProcesoDTO.setId_servicio(savedProceso.getId_servicio().getId());
			return savedProcesoDTO;
		}
		throw new EntidadNoExisteException("El Area de Servicio No se encuentra Registrada");
	}

	@Override
	public ProcesoDTO actualizarProceso(Integer id, ProcesoDTO procesoDTO) {
		Optional<Proceso> optionalProceso = procesosRepository.findById(id);
		if (optionalProceso.isPresent()) {
			Proceso proceso = optionalProceso.get();
			proceso.setNombre(procesoDTO.getNombre());
			proceso.setDescripcion(procesoDTO.getDescripcion());
			Optional<Servicio> areaServicioOpt = areaServicioRepository.findById(procesoDTO.getId_servicio());
			if (areaServicioOpt.isPresent()) {
				proceso.setId_servicio(areaServicioOpt.get());
			} else {
				throw new EntidadNoExisteException("El Area de Servicio No se encuentra Registrada");
			}
			Proceso updatedProceso = procesosRepository.save(proceso);
			ProcesoDTO updatedProcesoDTO = new ProcesoDTO();
			updatedProcesoDTO.setId(updatedProceso.getId());
			updatedProcesoDTO.setNombre(updatedProceso.getNombre());
			updatedProcesoDTO.setDescripcion(updatedProceso.getDescripcion());
			updatedProcesoDTO.setId_servicio(updatedProceso.getId_servicio().getId());
			return updatedProcesoDTO;
		} else {
			throw new EntidadNoExisteException("No se encontro el proceso");
		}
	}

}
