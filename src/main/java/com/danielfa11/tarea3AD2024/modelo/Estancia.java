/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danielfa11.tarea3AD2024.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Daniel Fernández Álvarez
 */

@Entity
@Table(name = "Estancias")
public class Estancia implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "fecha")
    private LocalDate fecha;
    
	@Column(name = "vip")
    private boolean vip = false;
    
	@ManyToOne
    @JoinColumn(name="IdParada")
    private Parada parada;
    
    @ManyToOne
    @JoinColumn(name="IdPeregrino")
    private Peregrino peregrino;
    
    // Constructores

    public Estancia(Long id, LocalDate fecha, Parada parada, Peregrino peregrino) {
        this.id = id;
        this.fecha = fecha;
        this.parada = parada;
        this.peregrino = peregrino;
    }

    public Estancia() {
    
    }
    
    // Getters y Setters

    
    
    public Long getId() {
        return id;
    }
    
	public void setId(Long id) {
	        this.id = id;
	    }
	
    public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }
    
    // Metodos

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.fecha);
        hash = 13 * hash + (this.vip ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.parada);
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
        final Estancia other = (Estancia) obj;
        if (this.vip != other.vip) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.parada, other.parada);
    }

    @Override
    public String toString() {
        return "Estancia{" + "id=" + id + ", fecha=" + fecha + ", vip=" + vip + ", parada=" + parada + '}';
    }
    
    
    
}
