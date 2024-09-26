package husjp.api.mesaprocesos.service.impl;

import husjp.api.mesaprocesos.config.JwtContext;
import husjp.api.mesaprocesos.request.ApiGatewayServiceRequest;
import husjp.api.mesaprocesos.service.UtilidadesService;
import husjp.api.mesaprocesos.service.dto.AuthenticationRequest;
import husjp.api.mesaprocesos.service.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class UtilisadesServiceImpl implements UtilidadesService {

    private ApiGatewayServiceRequest apiGatewayServiceRequest;
    private JwtContext jwtContext;


    @Override
    public byte[] generarExcel(HttpServletRequest request, Object data) {
        String username = (String) request.getAttribute("username");
        String password = (String) request.getAttribute("password");
        Map<String, Object> datosEnviar = new HashMap<>();
        datosEnviar.put("data", data);
        AuthenticationResponse authenticationResponse =  apiGatewayServiceRequest.loginMicroservices(new AuthenticationRequest(username, password));
        jwtContext.setToken(authenticationResponse.getJwt());
        return apiGatewayServiceRequest.exportExcel(datosEnviar);

    }

    public Object ejemplo(HttpServletRequest request, Object data) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("data", data);
        return respuesta;
    }
}
