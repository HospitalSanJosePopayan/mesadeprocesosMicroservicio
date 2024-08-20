package husjp.api.mesaprocesos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import husjp.api.mesaprocesos.entity.Proceso;

public interface ProcesoRepository extends JpaRepository<Proceso, Integer>  {

	 @Query("SELECT p FROM Proceso p WHERE p.idarea.idarea = :idarea")
	    List<Proceso> findByIdArea(@Param("idarea") Integer idarea);
}
