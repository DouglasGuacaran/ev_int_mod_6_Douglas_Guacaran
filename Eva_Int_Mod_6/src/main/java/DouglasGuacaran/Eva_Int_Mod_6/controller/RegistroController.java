package DouglasGuacaran.Eva_Int_Mod_6.controller;

import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger logger = LogManager.getLogger(RegistroController.class);

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password,
                               @RequestParam String nombre, @RequestParam String apellido,
                               @RequestParam String rut, @RequestParam String direccion, Model model) {
        try {
            logger.info("Registrando usuario con email: {}", email);
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setPassword(password);  // La contraseña se encripta en el servicio
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setRut(rut);
            usuario.setDireccion(direccion);
            usuario.setCreadoEn(LocalDateTime.now());

            usuarioService.save(usuario);

            logger.info("Usuario registrado exitosamente: {}", usuario);

            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            logger.error("Error al registrar usuario: {}", e.getMessage());
            model.addAttribute("error", "El correo electrónico o el RUT ya está registrado");
            return "register";
        }
    }
}
