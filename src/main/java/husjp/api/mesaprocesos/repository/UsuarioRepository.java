package husjp.api.mesaprocesos.repository;

import husjp.api.mesaprocesos.entity.Usuario;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query( value = "SELECT persona.documento, persona.nombre_completo,usuario.*FROM area_servicio INNER JOIN proceso ON area_servicio.idarea = proceso.id_area INNER JOIN  subproceso ON proceso.id_proceso = subproceso.idproceso INNER JOIN usuario_proceso ON subproceso.id_sub_proceso = usuario_proceso.subproceso_id INNER JOIN usuario ON usuario_proceso.usuario_id = usuario.id_persona INNER JOIN persona ON usuario.id_persona = persona.id_persona INNER JOIN areas_servicios_usuarios ON usuario.id_persona = areas_servicios_usuarios.id_persona AND area_servicio.idarea = areas_servicios_usuarios.idarea WHERE area_servicio.idarea = ?1", nativeQuery= true)
    List<Usuario> usuariosPorIdArea(Integer idArea);

    Optional<Usuario> findByDocumento(String documento);
}

