package husjp.api.mesaprocesos.service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    @NotEmpty(message = "el usuario no puede ser vacio")
    private String username;
    @NotEmpty(message = "el password no puede ser vacio")
    private String password;
}