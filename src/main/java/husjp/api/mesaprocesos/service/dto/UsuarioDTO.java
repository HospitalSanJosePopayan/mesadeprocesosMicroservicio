package husjp.api.mesaprocesos.service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class UsuarioDTO { @NotEmpty(message = "{usuario.documento.empty}")
	private String documento;
	private String nombrecompleto;
	private LocalDateTime fechaNacimiento;
	@NotEmpty(message = "usuario.documento.password")
	private String password;
	private Boolean estado;

}
