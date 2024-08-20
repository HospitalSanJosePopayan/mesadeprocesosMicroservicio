package husjp.api.mesaprocesos.service.impl;

import husjp.api.mesaprocesos.entity.Usuario;
import husjp.api.mesaprocesos.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("No se encontro usuario con username: "+username));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        usuario.getRoles().forEach(rol ->  authorityList.add(new SimpleGrantedAuthority("ROLE_"+rol.getRol())));

        return new User(usuario.getDocumento(),usuario.getPassword(),authorityList);
    }
}
