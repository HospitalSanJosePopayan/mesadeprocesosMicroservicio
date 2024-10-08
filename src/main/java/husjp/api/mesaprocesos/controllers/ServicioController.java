package husjp.api.mesaprocesos.controllers;

import java.util.List;

import husjp.api.mesaprocesos.service.dto.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import husjp.api.mesaprocesos.service.IServicioService;
import husjp.api.mesaprocesos.service.dto.ServicioDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/servicio")
@CrossOrigin(origins = {"http://localhost:5173", "http://optimus:5173"})
public class ServicioController {

    private IServicioService areaServicioService;
    @Operation(summary = "Listar todas las áreas de servicio Obtiene una lista de todas las áreas de servicio disponibles.")
    @GetMapping
    public ResponseEntity<List<ServicioDTO>> ListarAreasServicio() {
        return new ResponseEntity<>(areaServicioService.obtenerAreasServicio(), HttpStatus.OK);
    }
    @Operation(summary = "Listar Usuarios  por Area")
    @GetMapping("/usuarios/{id}")
    public  ResponseEntity<List<UsuarioDTO>> ListarUsuariosPorIdArea(@PathVariable(name = "id")Integer id){
       return  new ResponseEntity<>(areaServicioService.buscarUsuarioporArea(id),HttpStatus.OK);

    }

}
