package DouglasGuacaran.Eva_Int_Mod_6.service;

import java.util.List;

import org.springframework.stereotype.Service;

import DouglasGuacaran.Eva_Int_Mod_6.model.Cuenta;
import DouglasGuacaran.Eva_Int_Mod_6.model.Transaccion;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import DouglasGuacaran.Eva_Int_Mod_6.repository.TransaccionRepository;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public List<Transaccion> getTransaccionesPorUsuario(Usuario usuario){
        return transaccionRepository.findByUsuario(usuario);
    }
    
    public List<Transaccion> getTransaccionesPorCuenta(Cuenta cuenta){
        return transaccionRepository.findByCuenta(cuenta);
    }

    public void guardarTransaccion(Transaccion transaccion) {
        transaccionRepository.save(transaccion);
    }
}
