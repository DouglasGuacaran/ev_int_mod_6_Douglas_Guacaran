package DouglasGuacaran.Eva_Int_Mod_6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import DouglasGuacaran.Eva_Int_Mod_6.model.Cuenta;
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
            Cuenta cuenta = transacciones.isEmpty() ? null : transacciones.get(0).getCuenta();
            Double saldo = cuenta != null ? cuenta.getSaldo() : 0.0;
            model.addAttribute("transacciones", transacciones);
            model.addAttribute("saldo", saldo);
            model.addAttribute("transaccion", new Transaccion());
            return "transacciones";
        } else {
            return "error";
        }
    }

    @GetMapping("/transacciones/nueva")
    public String nuevaTransaccionForm(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            Cuenta cuenta = usuario.getCuentas().stream().findFirst().orElse(null);
            if (cuenta != null) {
                model.addAttribute("cuenta", cuenta);
                model.addAttribute("saldo", cuenta.getSaldo());
                model.addAttribute("transaccion", new Transaccion());
                return "nueva_transaccion";
            }
        }
        return "error";
    }


    @PostMapping("/transacciones")
    public String guardarTransaccion(Transaccion transaccion, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            transaccion.setUsuario(usuario);
            transaccion.setFecha(LocalDateTime.now());
            transaccionService.guardarTransaccion(transaccion);
            return "redirect:/transacciones";
        } else {
            return "error";
        }
    }
}

