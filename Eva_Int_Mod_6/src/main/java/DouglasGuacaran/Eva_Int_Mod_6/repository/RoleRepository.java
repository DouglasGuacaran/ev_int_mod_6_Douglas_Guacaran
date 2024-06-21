package DouglasGuacaran.Eva_Int_Mod_6.repository;

import DouglasGuacaran.Eva_Int_Mod_6.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
