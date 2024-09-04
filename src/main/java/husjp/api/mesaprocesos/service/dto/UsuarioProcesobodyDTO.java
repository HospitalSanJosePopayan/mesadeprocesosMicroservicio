package husjp.api.mesaprocesos.service.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UsuarioProcesobodyDTO {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String documento;
    private Integer IdsubProceso;
    private  Integer IdProceso;
}
