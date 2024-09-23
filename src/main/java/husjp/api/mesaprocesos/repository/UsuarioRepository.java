package husjp.api.mesaprocesos.repository;

import husjp.api.mesaprocesos.entity.Usuario;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {


    Optional<Usuario> findByDocumento(String documento);
}

