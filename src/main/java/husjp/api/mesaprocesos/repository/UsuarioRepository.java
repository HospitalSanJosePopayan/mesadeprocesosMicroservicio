package husjp.api.mesaprocesos.repository;

import husjp.api.mesaprocesos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
