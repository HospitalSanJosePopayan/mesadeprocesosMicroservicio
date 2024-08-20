package husjp.api.mesaprocesos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class SubProcesoImpl implements ISubProcesoService  {
 private SubProcesoRepository subProcesoRepository;
 private ProcesoRepository procesosRepository;
 private final ModelMapper modelMapper;
    @Override
    public List<SubProcesoDTO> obtenerSubprocesos() {
        return subProcesoRepository.findAll().stream()
        .map(subproceso -> {
            SubProcesoDTO dto = modelMapper.map(subproceso, SubProcesoDTO.class);
            dto.setProceso(subproceso.getProceso().getIdproceso()); // Mapear el ID del proceso
            return dto;
        })
        .collect(Collectors.toList());
    }
    @Override
    public List<SubProcesoDTO> obtenerSubprocesosporArea(Integer idProceso) {
      List<SubProceso> obtenersubprocesos = subProcesoRepository.findByIdProceso(idProceso);
      return obtenersubprocesos.stream()
      .map(subproceso -> modelMapper.map(subproceso, SubProcesoDTO.class))
      .collect(Collectors.toList());
    }


    @Override
    public SubProcesoDTO crearSubProceso(SubProcesoDTO subProcesoDTO) {
        SubProceso subProceso = new SubProceso();
        subProceso.setNombreSubproceso(subProcesoDTO.getNombreSubproceso());
        subProceso.setDescripcion(subProcesoDTO.getDescripcion());

        // Buscar el Proceso por idproceso
        Proceso proceso = procesosRepository.findById(subProcesoDTO.getProceso())
            .orElseThrow(() -> new IllegalArgumentException("Proceso no encontrado"));
        
        subProceso.setProceso(proceso);

        SubProceso savedSubProceso = subProcesoRepository.save(subProceso);
        return modelMapper.map(savedSubProceso, SubProcesoDTO.class);
    }
    @Override
    public SubProcesoDTO actualizarSubproceso(Integer id, SubProcesoDTO subProcesoDTO) {
        Optional<SubProceso> optionalSubProceso = subProcesoRepository.findById(id);
        if (optionalSubProceso.isPresent()) {
            SubProceso subProceso = optionalSubProceso.get();
            subProceso.setNombreSubproceso(subProcesoDTO.getNombreSubproceso());
            subProceso.setDescripcion(subProcesoDTO.getDescripcion());
        
            
            Proceso proceso = procesosRepository.findById(subProcesoDTO.getProceso())
                .orElseThrow(() -> new IllegalArgumentException("Proceso no encontrado"));
            subProceso.setProceso(proceso);
            
            SubProceso updatedSubProceso = subProcesoRepository.save(subProceso);
            return modelMapper.map(updatedSubProceso, SubProcesoDTO.class);
        } else {
            throw new RuntimeException("SubProceso no encontrado");
        }
    }

    
    
}
