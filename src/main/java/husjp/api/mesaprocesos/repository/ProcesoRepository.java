package husjp.api.mesaprocesos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import husjp.api.mesaprocesos.entity.Proceso;

public interface ProcesoRepository extends JpaRepository<Proceso, Integer>  {

	 @Query("SELECT p FROM Proceso p WHERE p.id_servicio.id = :id_servicio")
	    List<Proceso> findByIdArea(@Param("id_servicio") Integer id_servicio);
	@Query("SELECT COUNT(p) > 0 FROM Proceso p WHERE p.nombre = :nombre AND p.id_servicio.id = :id_servicio")
	boolean existsByNombreProceso(@Param("nombre") String nombre, @Param("id_servicio") Integer id_servicio);


}
