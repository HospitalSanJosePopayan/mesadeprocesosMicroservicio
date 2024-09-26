package husjp.api.mesaprocesos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class BasicAuthCredentialsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Basic")){
            //Decodificar la cabecera Authorization
            String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
            byte[] decodeBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodeBytes);

            //separar el username y la contraseña
            String [] usernameAndPassword = credentials.split((":"),2);
            String username = usernameAndPassword[0];
            String password = usernameAndPassword[1];

            //guardar en el request para usar en otra parte de la aplicación
            request.setAttribute("username", username);
            request.setAttribute("password", password);
        }

        //continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

}
