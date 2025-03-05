/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danielfa11.tarea3AD2024.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Daniel Fernández Álvarez
 */

@Entity
@Table(name = "Paradas")
public class Parada implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "nombre")
    private String nombre;
    
	@Column(name = "region")
    private char region;
    
	@Column(name = "responsable")
    private String responsable;
    
    @ManyToMany(mappedBy="paradas", fetch = FetchType.EAGER)
    private List <Peregrino> peregrinos = new ArrayList<>();
    
    @OneToMany(mappedBy="parada",cascade= CascadeType.ALL)
    private List <Estancia> estancias = new ArrayList<>();
    
    // Constructores   
    
    public Parada(Long id, String nombre, char region, String responsable) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;
        this.responsable = responsable;
    }
    
    public Parada() {
        
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

    public char getRegion() {
        return region;
    }

    public void setRegion(char region) {
        this.region = region;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public List<Peregrino> getPeregrinos() {
        return peregrinos;
    }

    public void setPeregrinos(List<Peregrino> peregrinos) {
        this.peregrinos = peregrinos;
    }    

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}
    
    // Metodos

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.nombre);
        hash = 53 * hash + this.region;
        hash = 53 * hash + Objects.hashCode(this.responsable);
        hash = 53 * hash + Objects.hashCode(this.peregrinos);
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
        final Parada other = (Parada) obj;
        if (this.region != other.region) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.responsable, other.responsable)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.peregrinos, other.peregrinos);
    }

    @Override
    public String toString() {
        return "Parada{" + "id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable +'}';
    }
    
    
}
