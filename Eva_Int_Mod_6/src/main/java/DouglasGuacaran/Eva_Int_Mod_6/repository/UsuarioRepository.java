 package DouglasGuacaran.Eva_Int_Mod_6.repository;

import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
