package husjp.api.mesaprocesos.repository;

import java.time.LocalDateTime;
import java.util.List;

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


}
