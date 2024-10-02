package husjp.api.mesaprocesos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadSinAsignaciones;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadYaExiste;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import husjp.api.mesaprocesos.entity.Proceso;
import husjp.api.mesaprocesos.entity.SubProceso;
import husjp.api.mesaprocesos.repository.ProcesoRepository;
import husjp.api.mesaprocesos.repository.SubProcesoRepository;
import husjp.api.mesaprocesos.service.ISubProcesoService;
import husjp.api.mesaprocesos.service.dto.SubProcesoDTO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubProcesoImpl implements ISubProcesoService {
    private SubProcesoRepository subProcesoRepository;
    private ProcesoRepository procesosRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SubProcesoDTO> obtenerSubprocesos() {
        List<SubProceso> subProcesos = subProcesoRepository.findAll();
        if (subProcesos.isEmpty()) {
            throw new EntidadSinAsignaciones("No existen subprocesos registrados");
        }
        List<SubProcesoDTO> subProcesoDTOS = new ArrayList<>();
        for (SubProceso subProceso : subProcesos) {
            SubProcesoDTO subProcesoDTO = new SubProcesoDTO();
            subProcesoDTO.setId(subProceso.getId());
            subProcesoDTO.setNombreSubproceso(subProceso.getNombreSubproceso());
            subProcesoDTO.setDescripcion(subProceso.getDescripcion());
            subProcesoDTO.setIdproceso(subProceso.getProceso().getId());
            subProcesoDTOS.add(subProcesoDTO);
        }
        return subProcesoDTOS;
    }

    @Override
    public List<SubProcesoDTO> obtenerSubprocesosporProceso(Integer idProceso) {
        Optional<Proceso> procesoOpt = procesosRepository.findById(idProceso);
        if (procesoOpt.isPresent()) {
            Proceso proceso = procesoOpt.get();
            if(!procesoOpt.get().getSubprocesos().isEmpty()) {
                return proceso.getSubprocesos().stream()
                        .map(subproceso -> {
                            SubProcesoDTO dto = new SubProcesoDTO();
                            dto.setId(subproceso.getId());
                            dto.setNombreSubproceso(subproceso.getNombreSubproceso());
                            dto.setDescripcion(subproceso.getDescripcion());
                            dto.setIdproceso(proceso.getId());
                            return dto;
                        })
                        .collect(Collectors.toList());
            }
            throw  new EntidadSinAsignaciones("No existen subprocesos para este proceso");
        }
        throw new EntidadNoExisteException("No existen Procesos con este ID");
    }

    @Override
    public SubProcesoDTO crearSubProceso(SubProcesoDTO subProcesoDTO) {
        boolean existeSubproceso = subProcesoRepository.existsByNombreSubprocesoAndProcesoId(subProcesoDTO.getNombreSubproceso(),subProcesoDTO.getIdproceso());
        if(existeSubproceso){
            throw new EntidadYaExiste("El Nombre de este subproceso ya existe");
        }
        SubProceso subProceso = new SubProceso();
        subProceso.setNombreSubproceso(subProcesoDTO.getNombreSubproceso());
        subProceso.setDescripcion(subProcesoDTO.getDescripcion());
        Proceso proceso = procesosRepository.findById(subProcesoDTO.getIdproceso())
                .orElseThrow(() -> new EntidadNoExisteException("Proceso no encontrado"));
        subProceso.setProceso(proceso);
        
        SubProceso savedSubProceso = subProcesoRepository.save(subProceso);
        SubProcesoDTO savedSubProcesoDTO = new SubProcesoDTO();
        savedSubProcesoDTO.setId(savedSubProceso.getId());
        savedSubProcesoDTO.setNombreSubproceso(savedSubProceso.getNombreSubproceso());
        savedSubProcesoDTO.setDescripcion(savedSubProceso.getDescripcion());
        savedSubProcesoDTO.setIdproceso(savedSubProceso.getProceso().getId());
        return savedSubProcesoDTO;
    }
    @Override
    public SubProcesoDTO actualizarSubproceso(Integer id, SubProcesoDTO subProcesoDTO) {
        // Buscar el SubProceso por su ID
        Optional<SubProceso> optionalSubProceso = subProcesoRepository.findById(id);

        if (optionalSubProceso.isPresent()) {
            SubProceso subProceso = optionalSubProceso.get();
            // Verificar si el nombre o el proceso han cambiado
            boolean nombreCambio = !subProceso.getNombreSubproceso().equalsIgnoreCase(subProcesoDTO.getNombreSubproceso());
            boolean procesoCambio = !subProceso.getProceso().getId().equals(subProcesoDTO.getIdproceso());
            // Si el nombre o el proceso cambiaron, validar que el nuevo nombre no exista para el proceso
            if (nombreCambio || procesoCambio) {
                boolean existeSubproceso = subProcesoRepository.existsByNombreSubprocesoAndProcesoId(
                        subProcesoDTO.getNombreSubproceso(), subProcesoDTO.getIdproceso());
                if (existeSubproceso) {
                    throw new EntidadYaExiste("El nombre de este subproceso ya existe para el proceso con ID: " + subProcesoDTO.getIdproceso());
                }
            }
            // Actualizar los valores del subproceso
            subProceso.setNombreSubproceso(subProcesoDTO.getNombreSubproceso());
            subProceso.setDescripcion(subProcesoDTO.getDescripcion());
            // Verificar si el proceso ha cambiado
            if (procesoCambio) {
                Proceso proceso = procesosRepository.findById(subProcesoDTO.getIdproceso())
                        .orElseThrow(() -> new EntidadNoExisteException("Proceso no encontrado"));
                subProceso.setProceso(proceso);
            }
            SubProceso updatedSubProceso = subProcesoRepository.save(subProceso);
            SubProcesoDTO response = modelMapper.map(updatedSubProceso, SubProcesoDTO.class);
            response.setIdproceso(updatedSubProceso.getProceso().getId());
            return response;
        } else {
            throw new EntidadNoExisteException("SubProceso no encontrado");
        }
    }


}
