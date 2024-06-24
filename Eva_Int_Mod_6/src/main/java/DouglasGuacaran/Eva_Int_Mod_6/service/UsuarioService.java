package DouglasGuacaran.Eva_Int_Mod_6.service;

import DouglasGuacaran.Eva_Int_Mod_6.model.CustomUserDetails;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        usuario.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new CustomUserDetails(usuario, grantedAuthorities);
    }

    public void save(Usuario usuario) {
    	usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }
}
