package husjp.api.mesaprocesos.service.dto;


import java.time.LocalDateTime;


import lombok.Data;

@Data
public class UsuarioProcesoDTO {
    
    private Integer id;
    private Integer estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String Idusuario;
    private Integer IdsubProceso;
    private String descripcionSubproceso;
    private String NombreUsuario;
    private String enlace;

}