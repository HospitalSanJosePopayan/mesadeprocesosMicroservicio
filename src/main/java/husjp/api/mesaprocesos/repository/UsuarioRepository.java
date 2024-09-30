package husjp.api.mesaprocesos.repository;

import husjp.api.mesaprocesos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByDocumento(String documento);

}

