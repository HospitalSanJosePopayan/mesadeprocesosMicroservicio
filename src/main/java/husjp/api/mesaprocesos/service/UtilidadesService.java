package husjp.api.mesaprocesos.service;

import jakarta.servlet.http.HttpServletRequest;

public interface UtilidadesService {

    byte[] generarExcel(HttpServletRequest request, Object data);
}
