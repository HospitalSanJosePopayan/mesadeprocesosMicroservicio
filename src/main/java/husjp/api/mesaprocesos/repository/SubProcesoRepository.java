package husjp.api.mesaprocesos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import husjp.api.mesaprocesos.entity.SubProceso;


public interface SubProcesoRepository extends JpaRepository<SubProceso, Integer> {

    @Query("SELECT s FROM SubProceso s WHERE s.proceso.id = :idproceso")
    List<SubProceso> findByIdProceso(@Param("idproceso") Integer idproceso);

    // se compara los nomnres del subprocesos en la tabla con el parametro en caso de tener una incidencia indica que el  nombre del subproceso existe
    @Query("SELECT COUNT(s) > 0 FROM SubProceso s WHERE s.nombreSubproceso = :nombreSubproceso AND s.proceso.id = :idproceso")
    boolean existsByNombreSubprocesoAndProcesoId(@Param("nombreSubproceso") String nombreSubproceso, @Param("idproceso") Integer idproceso);

}
