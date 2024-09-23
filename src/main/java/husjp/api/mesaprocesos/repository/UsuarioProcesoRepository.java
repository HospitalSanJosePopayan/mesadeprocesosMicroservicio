package husjp.api.mesaprocesos.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import husjp.api.mesaprocesos.entity.Proceso;
import husjp.api.mesaprocesos.entity.SubProceso;
import husjp.api.mesaprocesos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import husjp.api.mesaprocesos.entity.UsuarioProceso;

public interface UsuarioProcesoRepository extends JpaRepository<UsuarioProceso,Integer> {

    @Query("SELECT up FROM UsuarioProceso up WHERE up.usuario.id = :idUsuario")
    List<UsuarioProceso> findAllByUsuarioId(@Param("idUsuario") String idUsuario);
    List<UsuarioProceso> findAllByUsuarioDocumento(String documento);
    @Query("SELECT up FROM UsuarioProceso up WHERE up.estado IN (:estados) AND up.fechaFin < :fechaFinparam")
    List<UsuarioProceso> findByEstadoAndFechaFin(@Param("estados") List<Integer> estados, @Param("fechaFinparam") LocalDateTime fechaFinparam);
    @Query("SELECT up FROM UsuarioProceso up WHERE up.usuario.id = :usuarioId AND up.subProceso.id = :subProcesoId AND up.estado IN(1,3) ")
    Optional<UsuarioProceso> findUsuarioProcesoEnCurso(@Param("usuarioId") Integer usuarioId, @Param("subProcesoId") Integer subProcesoId);

    @Query(value = "SELECT usuario_proceso.* FROM area_servicio  INNER JOIN proceso ON area_servicio.idarea = proceso.id_area  INNER JOIN subproceso ON proceso.id_proceso = subproceso.idproceso  INNER JOIN usuario_proceso ON subproceso.id_sub_proceso = usuario_proceso.subproceso_id INNER JOIN usuario ON usuario_proceso.usuario_id = usuario.id_persona  INNER JOIN persona ON usuario.id_persona = persona.id_persona INNER JOIN areas_servicios_usuarios ON usuario.id_persona = areas_servicios_usuarios.id_persona AND area_servicio.idarea = areas_servicios_usuarios.idarea WHERE area_servicio.idarea = ?1", nativeQuery = true)
    List<UsuarioProceso> usuariosPorArea(Integer idArea);


}
