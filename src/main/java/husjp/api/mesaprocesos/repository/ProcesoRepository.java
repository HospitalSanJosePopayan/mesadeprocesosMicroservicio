package husjp.api.mesaprocesos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import husjp.api.mesaprocesos.entity.Proceso;

public interface ProcesoRepository extends JpaRepository<Proceso, Integer>  {

	 @Query("SELECT p FROM Proceso p WHERE p.idarea.id = :idarea")
	    List<Proceso> findByIdArea(@Param("idarea") Integer idarea);
	@Query("SELECT COUNT(p) > 0 FROM Proceso p WHERE p.nombre = :nombre AND p.idarea.id = :idarea")
	boolean existsByNombreProceso(@Param("nombre") String nombre, @Param("idarea") Integer idarea);

}
