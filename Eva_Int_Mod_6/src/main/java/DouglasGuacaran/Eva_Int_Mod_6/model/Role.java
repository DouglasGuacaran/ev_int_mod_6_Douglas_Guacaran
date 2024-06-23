package DouglasGuacaran.Eva_Int_Mod_6.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
	// Atributos de la clase Role
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// Nombre del rol
	@Column(name = "role_name")
	private String roleName;
	// Relacion muchos a muchos con la clase Usuario
	@ManyToMany(mappedBy = "roles")
	private Set<Usuario> usuarios;

	// Se generan los setters y getters de los atributos de la clase Role
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Role(Long id, String roleName, Set<Usuario> usuarios) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.usuarios = usuarios;
	}

	public Role() {
		super();
	}

}
