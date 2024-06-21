package DouglasGuacaran.Eva_Int_Mod_6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DouglasGuacaran.Eva_Int_Mod_6.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
