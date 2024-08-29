package husjp.api.mesaprocesos.repository;

import husjp.api.mesaprocesos.entity.Usuario;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query(value = "SELECT persona.documento,persona.nombre_completo, usuario.* FROM usuario INNER JOIN usuario_proceso ON  usuario.id_persona = usuario_proceso.usuario_id INNER JOIN subproceso ON usuario_proceso.subproceso_id = subproceso.id_sub_proceso INNER JOIN proceso ON subproceso.idproceso = proceso.id_proceso INNER JOIN area_servicio ON proceso.id_area = area_servicio.idarea INNER JOIN persona ON usuario.id_persona = persona.id_persona WHERE area_servicio.idarea = ?1", nativeQuery = true)
    List<Usuario> usuariosPorIdArea(Integer idArea);

    Optional<Usuario> findByDocumento(String documento);
}
