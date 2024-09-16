package husjp.api.mesaprocesos.controllers;

import java.util.List;

import husjp.api.mesaprocesos.service.dto.ProcesoDTO;
import husjp.api.mesaprocesos.service.dto.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<AreaServicioDTO>> ListarAreasServicio() {
        return new ResponseEntity<>(areaServicioService.obtenerAreasServicio(), HttpStatus.OK);
    }
    @Operation(summary = "Listar Usuarios  por Area")
    @GetMapping("/usuarios/{id}")
    public  ResponseEntity<List<UsuarioDTO>> ListarUsuariosPorIdArea(@PathVariable(name = "id")Integer id){
       return  new ResponseEntity<>(areaServicioService.buscarUsuarioporArea(id),HttpStatus.OK);

    }

}
