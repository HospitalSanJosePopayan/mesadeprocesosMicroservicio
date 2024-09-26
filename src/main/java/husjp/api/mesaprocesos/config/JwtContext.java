package husjp.api.mesaprocesos.config;

import org.springframework.stereotype.Component;

// Clase que se encarga de almacenar el token JWT en un hilo local
@Component
public class JwtContext {

    // Almacena el token JWT en un hilo local
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    // Establece el token JWT en un hilo local
    public void setToken(String token) {
        tokenHolder.set(token);
    }

    //metodo para obtener el token
    public String getToken() {
        return tokenHolder.get();
    }

    //metodo para limpiar el token
    public void clear() {
        tokenHolder.remove();
    }
}
