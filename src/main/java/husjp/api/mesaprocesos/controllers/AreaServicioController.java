package husjp.api.mesaprocesos.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import husjp.api.mesaprocesos.service.IAreaServicioService;
import husjp.api.mesaprocesos.service.dto.AreaServicioDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/AreaServicio")
@CrossOrigin(origins = {"http://localhost:5173", "http://optimus:5173"})
public class AreaServicioController {

    private IAreaServicioService areaServicioService;

    @Operation(summary = "Listar todas las áreas de servicio Obtiene una lista de todas las áreas de servicio disponibles.")
    @GetMapping
    public List<AreaServicioDTO> ListarAreasServicio() {
        return areaServicioService.obtenerAreasServicio();
    }

}
