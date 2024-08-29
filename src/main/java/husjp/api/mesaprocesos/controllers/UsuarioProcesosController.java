package husjp.api.mesaprocesos.controllers;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import husjp.api.mesaprocesos.service.IUsuarioProcesoService;
import husjp.api.mesaprocesos.service.dto.UsuarioProcesoDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarioprocesos")
@CrossOrigin(origins = {"http://localhost:5173", "http://optimus:5173"})
public class UsuarioProcesosController {

    private IUsuarioProcesoService usuarioProcesoService;

    @Operation(summary = "obtener todos los usuarios con subprocesos asignados")
    @GetMapping
    public List<UsuarioProcesoDTO> listarSubprocesosUsuarios() {
        return usuarioProcesoService.obtenerUsuariosprocesos();
    }

    @Operation(summary = "obtener todos los subprocesos asignados a un usuario especifico")
    @GetMapping("/{usuarioid}")
    public List<UsuarioProcesoDTO> listarUsurioProceso(@PathVariable(name = "usuarioid") String usuarioid) {
        return usuarioProcesoService.obtenerprocesosPorUsuario(usuarioid);
    }
    @Operation(summary = "Asignar un subproceso a un usuario especifico")
    @PostMapping
    public UsuarioProcesoDTO crearUsuarioSubProceso(@RequestBody UsuarioProcesoDTO usuarioProcesoDTO) {
        return usuarioProcesoService.crearUsuarioProceso(usuarioProcesoDTO);

    }
     @Operation(summary = "actualiza la fecha fin de un subproceso asignado a un usuario")
    @PutMapping("/{id}")
    public UsuarioProcesoDTO actualizarFechaFin(@PathVariable(name = "id") Integer id, @RequestBody LocalDateTime nuevaFechaFin) {
        return usuarioProcesoService.actualizarUsuarioProcesoFecha(id, nuevaFechaFin);
    }
    @Operation(summary = "Actualiza el estado de un subproceso asignado aun usuario ")
    @PutMapping("/estado/{proceso}")
    public UsuarioProcesoDTO actualizarestado(@PathVariable(name = "proceso") Integer proceso, @RequestParam(name = "enlace") String enlace) {
        return usuarioProcesoService.actualizarUsuarioprocesoEstado(proceso, enlace);
    }
    @Operation(summary = "elimina la asignacion de un subproceso a un usuario especifico")
    @DeleteMapping("/{id}")
    public void eliminarUsuarioProceso(@PathVariable(name = "id") Integer id) {
        usuarioProcesoService.eliminarUsuarioProceso(id);
    }
    @Operation(summary = "lista los  subprocesos asignados a ls usuarios por un area especifica")
    @GetMapping("/area/{idArea}")
    public List<UsuarioProcesoDTO> getUsuarioProcesosByArea(@PathVariable(name = "idArea") Integer idArea) {
        return usuarioProcesoService.obtenerUsuarioProcesoArea(idArea);
    }

    @Operation(summary = "Trasfiere un subproceso asignado a un usaurio a otro usuario")
    @PutMapping("/transferir")
    public UsuarioProcesoDTO transferirSubproceso(
            @RequestParam(name = "idUsuarioProceso") Integer idUsuarioProceso,
            @RequestParam(name = "nuevoUsuarioId") String nuevoUsuarioId) {
        return usuarioProcesoService.transferirSubprocesoAUsuario(idUsuarioProceso, nuevoUsuarioId);
    }


}
