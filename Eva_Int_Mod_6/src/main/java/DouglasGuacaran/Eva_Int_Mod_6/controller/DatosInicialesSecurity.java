package DouglasGuacaran.Eva_Int_Mod_6.controller;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import DouglasGuacaran.Eva_Int_Mod_6.model.Cuenta;
import DouglasGuacaran.Eva_Int_Mod_6.model.Role;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.repository.CuentaRepository;
import DouglasGuacaran.Eva_Int_Mod_6.repository.RoleRepository;
import DouglasGuacaran.Eva_Int_Mod_6.repository.UsuarioRepository;

@Component
public class DatosInicialesSecurity implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear un usuario
        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();
        String passSinCifrar = "sa";
        String passSinCifrar2 = "sa";
        String passCifrado = cifrador.encode(passSinCifrar);
        String passCifrado2 = cifrador.encode(passSinCifrar2);

        // Creating roles
        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleRepository.save(userRole); // Save the role to the database

        // Creating sets of roles
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        // Instantiating Usuario objects
        Usuario usuarioInsertar = new Usuario("263492137", "Douglas", "Guacaran", "Santiago de Chile", "douglasguacaran@gmail.com", passCifrado, LocalDateTime.now(), roles);
        usuarioRepository.save(usuarioInsertar);
        Cuenta cuenta1 = new Cuenta(usuarioInsertar, 1000.0, LocalDateTime.now());
        cuentaRepository.save(cuenta1);
        
        Usuario usuarioInsertar2 = new Usuario("15151515-1","Jorge", "Alvarez","Buenos Aires", "jorgepereyra@gmail.com", passCifrado2, LocalDateTime.now(), roles);
        usuarioRepository.save(usuarioInsertar2);
        Cuenta cuenta2 = new Cuenta(usuarioInsertar2, 2000.0, LocalDateTime.now());
        cuentaRepository.save(cuenta2);
        
    }
    
}

