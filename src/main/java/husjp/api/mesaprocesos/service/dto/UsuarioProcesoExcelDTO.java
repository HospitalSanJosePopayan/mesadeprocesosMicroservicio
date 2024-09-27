package husjp.api.mesaprocesos.service.dto;

import lombok.Data;

@Data
public class UsuarioProcesoExcelDTO {
    private Integer idUsuarioProceso;
    private String documento;
    private String NombreUsuario;
    private Integer IdProceso;
    private String nombreSubProceso;
    private String descripcionSubproceso;
    private String estado;
    private String fechaInicioProceso;
    private String fechaFinProceso;
    private String enlace;
}
