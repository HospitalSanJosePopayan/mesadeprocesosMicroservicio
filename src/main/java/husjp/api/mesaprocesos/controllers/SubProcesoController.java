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

import husjp.api.mesaprocesos.service.ISubProcesoService;
import husjp.api.mesaprocesos.service.dto.SubProcesoDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/subprocesos")
@CrossOrigin(origins = { "http://localhost:5173", "http://optimus:5173" })
public class SubProcesoController {

    private ISubProcesoService subProcesoService;

    @Operation(summary = "Obtiene una lista de todos los subprocesos creados  ")
    @GetMapping
    public ResponseEntity<List<SubProcesoDTO>> listarSubprocesos() {

        return new ResponseEntity<>(subProcesoService.obtenerSubprocesos(), HttpStatus.OK);
    }

    @Operation(summary = "obtiene todos los subprocesos por un proceso en especifico")
    @GetMapping("/{idProceso}")
    public ResponseEntity<List<SubProcesoDTO>> listarSubprocesosPorArea(
            @PathVariable(name = "idProceso") Integer idProceso) {
        return new ResponseEntity<>(subProcesoService.obtenerSubprocesosporProceso(idProceso), HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo subproceso para un proceso en Especifico")
    @PostMapping
    public ResponseEntity<SubProcesoDTO> creaSubProcesoDTO(@RequestBody SubProcesoDTO subProcesoDTO) {
        return new ResponseEntity<>(subProcesoService.crearSubProceso(subProcesoDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza un  subproceso especifico a travez de su Id")
    @PutMapping("/{id}")
    public ResponseEntity<SubProcesoDTO> actualizarSubProceso(@PathVariable(name = "id") Integer id,
            @RequestBody SubProcesoDTO subProcesoDTO) {
        return new ResponseEntity<>(subProcesoService.actualizarSubproceso(id, subProcesoDTO), HttpStatus.OK);
    }

}