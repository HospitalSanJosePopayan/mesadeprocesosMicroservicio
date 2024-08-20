package husjp.api.mesaprocesos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import husjp.api.mesaprocesos.entity.SubProceso;



public interface SubProcesoRepository extends JpaRepository<SubProceso,Integer> {
    @Query("SELECT s FROM SubProceso s WHERE s.proceso.idproceso = :idproceso")
    List<SubProceso> findByIdProceso(@Param("idproceso") Integer idproceso);
}
