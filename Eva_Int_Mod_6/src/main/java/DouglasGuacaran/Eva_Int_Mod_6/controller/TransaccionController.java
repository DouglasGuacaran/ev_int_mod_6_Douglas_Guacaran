package DouglasGuacaran.Eva_Int_Mod_6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import DouglasGuacaran.Eva_Int_Mod_6.model.Transaccion;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.repository.UsuarioRepository;
import DouglasGuacaran.Eva_Int_Mod_6.service.TransaccionService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TransaccionController {
    private final TransaccionService transaccionService;
    private final UsuarioRepository usuarioRepository;

    public TransaccionController(TransaccionService transaccionService, UsuarioRepository usuarioRepository) {
        this.transaccionService = transaccionService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/transacciones")
    public String verTransacciones(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            List<Transaccion> transacciones = transaccionService.getTransaccionesPorUsuario(usuario);
            model.addAttribute("transacciones", transacciones);
            return "transacciones";
        } else {
            // Manejar caso donde el usuario no fue encontrado
            return "error";
        }
    }

    @GetMapping("/transacciones/nueva")
    public String nuevaTransaccionForm(Model model) {
        model.addAttribute("transaccion", new Transaccion());
        return "nueva_transaccion";
    }

    @PostMapping("/transacciones")
    public String guardarTransaccion(Transaccion transaccion, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            transaccion.setUsuario(usuario); // Asociar el usuario con la transacción
            transaccion.setFecha(LocalDateTime.now()); // Establecer la fecha de la transacción
            transaccionService.guardarTransaccion(transaccion); // Guardar la transacción
            return "redirect:/transacciones";
        } else {
            // Manejar caso donde el usuario no fue encontrado
            return "error";
        }
    }
}
