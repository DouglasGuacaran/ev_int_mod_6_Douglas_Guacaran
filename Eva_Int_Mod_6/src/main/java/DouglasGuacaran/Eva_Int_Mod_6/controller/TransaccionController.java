package DouglasGuacaran.Eva_Int_Mod_6.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import DouglasGuacaran.Eva_Int_Mod_6.model.Cuenta;
import DouglasGuacaran.Eva_Int_Mod_6.model.Transaccion;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.repository.CuentaRepository;
import DouglasGuacaran.Eva_Int_Mod_6.repository.UsuarioRepository;
import DouglasGuacaran.Eva_Int_Mod_6.service.TransaccionService;

@Controller
public class TransaccionController {
    private final TransaccionService transaccionService;
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionController(TransaccionService transaccionService, UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository) {
        this.transaccionService = transaccionService;
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @GetMapping("/transacciones")
    public String verTransacciones(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            Cuenta cuenta = usuario.getCuentas().stream().findFirst().orElse(null);
            if (cuenta != null) {
                List<Transaccion> transacciones = transaccionService.getTransaccionesPorCuenta(cuenta);
                Double saldo = cuenta.getSaldo();
                model.addAttribute("transacciones", transacciones);
                model.addAttribute("saldo", saldo);
                model.addAttribute("transaccion", new Transaccion());
                return "transacciones";
            }
        }
        return "error";
    }

    @GetMapping("/transacciones/nueva")
    public String nuevaTransaccionForm(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            Cuenta cuenta = usuario.getCuentas().stream().findFirst().orElse(null);
            if (cuenta != null) {
                List<Usuario> usuarios = usuarioRepository.findAll().stream()
                        .filter(u -> !u.getEmail().equals(email)) // Exclude the current user
                        .collect(Collectors.toList());
                model.addAttribute("usuarios", usuarios);
                model.addAttribute("cuenta", cuenta);
                model.addAttribute("saldo", cuenta.getSaldo());
                model.addAttribute("transaccion", new Transaccion());
                return "nueva_transaccion";
            }
        }
        return "error";
    }

    @PostMapping("/transacciones")
    public String guardarTransaccion(Transaccion transaccion, Principal principal, Model model) {
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            Cuenta cuenta = usuario.getCuentas().stream().findFirst().orElse(null);
            if (cuenta != null) {
                Double monto = transaccion.getMonto();
                Transaccion.TipoTransaccion tipo = transaccion.getTipo();

                // Validar y actualizar saldo según el tipo de transacción
                if (tipo == Transaccion.TipoTransaccion.DEPOSITO) {
                    cuenta.setSaldo(cuenta.getSaldo() + monto);
                } else if (tipo == Transaccion.TipoTransaccion.RETIRO) {
                    if (cuenta.getSaldo() >= monto) {
                        cuenta.setSaldo(cuenta.getSaldo() - monto);
                    } else {
                        model.addAttribute("error", "Saldo insuficiente para realizar el retiro.");
                        model.addAttribute("transaccion", transaccion);
                        model.addAttribute("saldo", cuenta.getSaldo());
                        return "nueva_transaccion";
                    }
                } else if (tipo == Transaccion.TipoTransaccion.ENVIO) {
                    Usuario usuarioDestinatario = usuarioRepository.findById(transaccion.getUsuario().getId()).orElse(null);
                    if (usuarioDestinatario != null) {
                        Cuenta cuentaDestinatario = usuarioDestinatario.getCuentas().stream().findFirst().orElse(null);
                        if (cuentaDestinatario != null) {
                            if (cuenta.getSaldo() >= monto) {
                                cuenta.setSaldo(cuenta.getSaldo() - monto);
                                cuentaDestinatario.setSaldo(cuentaDestinatario.getSaldo() + monto);
                                
                                // Crear transacción de envío
                                transaccion.setUsuario(usuario);
                                transaccion.setCuenta(cuenta);
                                transaccion.setFecha(LocalDateTime.now());
                                transaccion.setCuentaDestino(cuentaDestinatario);
                                transaccionService.guardarTransaccion(transaccion);
                                
                                // Crear transacción de recepción para el destinatario
                                Transaccion transaccionRecepcion = new Transaccion();
                                transaccionRecepcion.setUsuario(usuarioDestinatario);
                                transaccionRecepcion.setCuenta(cuentaDestinatario);
                                transaccionRecepcion.setFecha(LocalDateTime.now());
                                transaccionRecepcion.setMonto(monto);
                                transaccionRecepcion.setTipo(Transaccion.TipoTransaccion.RECEPCION);
                                transaccionRecepcion.setDescripcion("Recepción de dinero desde " + usuario.getNombre() + " " + usuario.getApellido());
                                transaccionService.guardarTransaccion(transaccionRecepcion);

                                cuentaRepository.save(cuenta);
                                cuentaRepository.save(cuentaDestinatario);
                                
                                return "redirect:/transacciones";
                            } else {
                                model.addAttribute("error", "Saldo insuficiente para realizar el envío.");
                                model.addAttribute("transaccion", transaccion);
                                model.addAttribute("saldo", cuenta.getSaldo());
                                return "nueva_transaccion";
                            }
                        }
                    }
                }

                transaccion.setUsuario(usuario);
                transaccion.setCuenta(cuenta);
                transaccion.setFecha(LocalDateTime.now());
                transaccionService.guardarTransaccion(transaccion);
                cuentaRepository.save(cuenta); // Save the updated account
                return "redirect:/transacciones";
            }
        }
        return "error";
    }
}
