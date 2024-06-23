package DouglasGuacaran.Eva_Int_Mod_6.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

public class CustomUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
    private final Usuario usuario;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario, Set<GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities != null ? authorities : new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
