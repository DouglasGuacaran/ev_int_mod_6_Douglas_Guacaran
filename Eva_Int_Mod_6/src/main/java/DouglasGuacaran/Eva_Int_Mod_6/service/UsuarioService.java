package DouglasGuacaran.Eva_Int_Mod_6.service;

import DouglasGuacaran.Eva_Int_Mod_6.model.Role;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.repository.RoleRepository;
import DouglasGuacaran.Eva_Int_Mod_6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar rol
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByRoleName("USER");
        userRole.ifPresent(roles::add);
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }
}
