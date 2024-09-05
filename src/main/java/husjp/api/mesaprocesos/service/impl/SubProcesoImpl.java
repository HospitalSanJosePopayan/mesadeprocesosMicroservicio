package husjp.api.mesaprocesos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadSinAsignaciones;
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
            subProcesoDTO.setIdSubProceso(subProceso.getIdSubProceso());
            subProcesoDTO.setNombreSubproceso(subProceso.getNombreSubproceso());
            subProcesoDTO.setDescripcion(subProceso.getDescripcion());
            subProcesoDTO.setIdproceso(subProceso.getProceso().getIdproceso());
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
                            dto.setIdSubProceso(subproceso.getIdSubProceso());
                            dto.setNombreSubproceso(subproceso.getNombreSubproceso());
                            dto.setDescripcion(subproceso.getDescripcion());
                            dto.setIdproceso(proceso.getIdproceso());
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
        SubProceso subProceso = new SubProceso();
        subProceso.setNombreSubproceso(subProcesoDTO.getNombreSubproceso());
        subProceso.setDescripcion(subProcesoDTO.getDescripcion());
        Proceso proceso = procesosRepository.findById(subProcesoDTO.getIdproceso())
                .orElseThrow(() -> new EntidadNoExisteException("Proceso no encontrado"));
        subProceso.setProceso(proceso);
        SubProceso savedSubProceso = subProcesoRepository.save(subProceso);
        SubProcesoDTO savedSubProcesoDTO = new SubProcesoDTO();
        savedSubProcesoDTO.setIdSubProceso(savedSubProceso.getIdSubProceso());
        savedSubProcesoDTO.setNombreSubproceso(savedSubProceso.getNombreSubproceso());
        savedSubProcesoDTO.setDescripcion(savedSubProceso.getDescripcion());
        savedSubProcesoDTO.setIdproceso(savedSubProceso.getProceso().getIdproceso());
        return savedSubProcesoDTO;
    }
    @Override
    public SubProcesoDTO actualizarSubproceso(Integer id, SubProcesoDTO subProcesoDTO) {
        Optional<SubProceso> optionalSubProceso = subProcesoRepository.findById(id);
        if (optionalSubProceso.isPresent()) {
             SubProceso subProceso = optionalSubProceso.get();
            subProceso.setNombreSubproceso(subProcesoDTO.getNombreSubproceso());
            subProceso.setDescripcion(subProcesoDTO.getDescripcion());
            Proceso proceso = procesosRepository.findById(subProcesoDTO.getIdproceso())
                    .orElseThrow(() -> new EntidadNoExisteException("Proceso no encontrado"));
            subProceso.setProceso(proceso);
            SubProceso updatedSubProceso = subProcesoRepository.save(subProceso);
            SubProcesoDTO response = modelMapper.map(updatedSubProceso, SubProcesoDTO.class);
            response.setIdproceso(updatedSubProceso.getProceso().getIdproceso());
            return response;
        } else {
            throw new EntidadNoExisteException("SubProceso no encontrado");
        }
    }
}
