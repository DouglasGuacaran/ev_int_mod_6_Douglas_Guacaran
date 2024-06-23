package DouglasGuacaran.Eva_Int_Mod_6.dto;

public class UsuarioDTO {

	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	private String password;

	// Constructor con todos los atributos
	public UsuarioDTO(Long id, String nombre, String apellido, String email, String password) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
	}

	// Constructor vacío
	public UsuarioDTO() {
	}

	// Constructor sin id
	public UsuarioDTO(String nombre, String apellido, String email, String password) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
	}

	// Constructor solo con email

	public UsuarioDTO(String email) {
		super();
		this.email = email;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
