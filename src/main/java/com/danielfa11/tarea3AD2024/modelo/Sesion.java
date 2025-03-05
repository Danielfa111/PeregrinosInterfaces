/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danielfa11.tarea3AD2024.modelo;

import java.util.Objects;

/**
 *
 * @author Daniel Fernández Álvarez
 */
public class Sesion {
    
	private static Sesion instance;
	
    private Long id;
    private String usuario;
    private String perfil;
    private String correo;
    
    // Constructores

    private Sesion(Long id, String usuario, String perfil) {
        this.id = id;
        this.usuario = usuario;
        this.perfil = perfil;
    }
    
    public static Sesion getSesion()
    {
    	if(instance == null)
		{
			instance = new Sesion();
		}
		return instance;
    }
    

    private Sesion() {
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
    
    // Metodos

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.usuario);
        hash = 23 * hash + Objects.hashCode(this.perfil);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sesion other = (Sesion) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return this.perfil == other.perfil;
    }

    @Override
    public String toString() {
        return "Sesion{" + "id=" + id + ", usuario=" + usuario + ", perfil=" + perfil + '}';
    }
    
    
}
