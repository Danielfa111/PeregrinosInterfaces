package com.danielfa11.tarea3AD2024.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "contrasenya")
	private String contraseña;
	
	@Column(name = "rol")
	private String rol;
	
	@Column(name = "correo")
	private String correo;
	
	// Constructores
	
	public Usuario(Long id, String usuario, String contraseña, String correo) {
		this.id = id;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.correo = correo;
	}
	
	public Usuario() {
		
	}
	
	// Getters y Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	//
	
}
