package husjp.api.mesaprocesos.controllers;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping
    public List<UsuarioProcesoDTO> listarSubprocesosUsuarios() {
        return usuarioProcesoService.obtenerUsuariosprocesos();
    }

    @GetMapping("/{usuarioid}")
    public List<UsuarioProcesoDTO> listarUsurioProceso(@PathVariable(name = "usuarioid") String usuarioid) {
        return usuarioProcesoService.obtenerprocesosPorUsuario(usuarioid);
    }

    @PostMapping
    public UsuarioProcesoDTO crearUsuarioSubProceso(@RequestBody UsuarioProcesoDTO usuarioProcesoDTO) {
        return usuarioProcesoService.crearUsuarioProceso(usuarioProcesoDTO);

    }

    @PutMapping("/{id}")
    public UsuarioProcesoDTO actualizarFechaFin(@PathVariable(name = "id") Integer id, @RequestBody LocalDateTime nuevaFechaFin) {
        return usuarioProcesoService.actualizarUsuarioProcesoFecha(id, nuevaFechaFin);
    }

    @PutMapping("/estado/{proceso}")
    public UsuarioProcesoDTO actualizarestado(@PathVariable(name = "proceso") Integer proceso, @RequestParam(name = "enlace") String enlace) {
        return usuarioProcesoService.actualizarUsuarioprocesoEstado(proceso, enlace);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuarioProceso(@PathVariable(name = "id") Integer id) {
        usuarioProcesoService.eliminarUsuarioProceso(id);
    }

    @GetMapping("/area/{idArea}")
    public List<UsuarioProcesoDTO> getUsuarioProcesosByArea(@PathVariable(name = "idArea") Integer idArea) {
        return usuarioProcesoService.obtenerUsuarioProcesoArea(idArea);
    }

    @PutMapping("/transferir")
    public UsuarioProcesoDTO transferirSubproceso(
            @RequestParam(name = "idUsuarioProceso") Integer idUsuarioProceso,
            @RequestParam(name = "nuevoUsuarioId") String nuevoUsuarioId) {
        return usuarioProcesoService.transferirSubprocesoAUsuario(idUsuarioProceso, nuevoUsuarioId);
    }


}
