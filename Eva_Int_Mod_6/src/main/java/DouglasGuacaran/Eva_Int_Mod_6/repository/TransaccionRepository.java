package DouglasGuacaran.Eva_Int_Mod_6.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import DouglasGuacaran.Eva_Int_Mod_6.model.Transaccion;
import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long>{
	List<Transaccion> findByUsuario(Usuario usuario);

}
