package DouglasGuacaran.Eva_Int_Mod_6.model;

 import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity
@Table(name = "cuenta") 
public class Cuenta {
	//Entidad de cuenta con sus respectivos atributos getters setters y constructores 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Double saldo = 0.0;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDateTime creadoEn) {
		this.creadoEn = creadoEn;
	}
	//Constructor con todos los atributos
	public Cuenta(Long id, Usuario usuario, Double saldo, LocalDateTime creadoEn) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.saldo = saldo;
		this.creadoEn = creadoEn;
	}
	//Constructor con los atributos de usuario sin id
	public Cuenta(Usuario usuario, Double saldo, LocalDateTime creadoEn) {
		super();
		this.usuario = usuario;
		this.saldo = saldo;
		this.creadoEn = creadoEn;
	}
	//Constructor vacio
	public Cuenta() {
		super();
	}
    
    
}
