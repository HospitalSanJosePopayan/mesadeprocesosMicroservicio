package husjp.api.mesaprocesos.controllers;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import husjp.api.mesaprocesos.service.IProcesoService;
import husjp.api.mesaprocesos.service.dto.ProcesoDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/procesos")
@CrossOrigin(origins = {"http://localhost:5173", "http://optimus:5173"})
public class ProcesoController {

    private IProcesoService procesoService;

    @Operation(summary = "Solicita todos los procesos registrados en las diferentes Areas de Servicio")
    @GetMapping
    public ResponseEntity<List<ProcesoDTO>> listarProcesos() {
        return new ResponseEntity<>(procesoService.obtenerProcesos(), HttpStatus.OK);
    }
    @Operation(summary = "Solicita los Procesos por una Area en Especifico")
    @GetMapping("/{idarea}")
    public  ResponseEntity <List<ProcesoDTO>> listarProcesosporArea(@PathVariable(name = "idarea") Integer idarea) {
        return new ResponseEntity<>( procesoService.obtenerProcesosArea(idarea), HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo proceso en una Area Especifica")
    @PostMapping
    public ResponseEntity<ProcesoDTO> crearProceso(@RequestBody ProcesoDTO procesoDTO) {
        return new ResponseEntity<>(procesoService.crearProceso(procesoDTO), HttpStatus.CREATED);
    }
    @Operation(summary = "Actualiza un Proceso  mediante su Id")
    @PutMapping("/{id}")
    public  ResponseEntity<ProcesoDTO> actualizarProceso(@PathVariable(name = "id") Integer id, @RequestBody ProcesoDTO procesoDTO) {
        return new ResponseEntity<>(procesoService.actualizarProceso(id, procesoDTO), HttpStatus.CREATED);
    }

}
