/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danielfa11.tarea3AD2024.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 *
 * @author Daniel Fernández Álvarez
 */

@Entity
@Table(name = "Carnets")
public class Carnet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Fecha de expedicion")
    private LocalDate fechaexp = LocalDate.now();
	
	@Column(name = "Distancia recorrida")
    private double distancia = 0.0;
	
	@Column(name = "Estancias vips")
    private int nvips = 0;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Parada paradaInicial;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Peregrino propietario;
    
   
	// Constructores
    
    public Carnet(Long id, LocalDate fechaexp, double distancia, int nvips, Parada paradaInicial,
			Peregrino propietario) {
		super();
		this.id = id;
		this.fechaexp = fechaexp;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaInicial = paradaInicial;
		this.propietario = propietario;
	}

    public Carnet(Long id, Parada paradaInicial, Peregrino propietario) {
        this.id = id;
        this.paradaInicial = paradaInicial;
        this.propietario = propietario;
    }  
    
    public Carnet(){
        
    }
    
    
    
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaexp() {
        return fechaexp;
    }

    public void setFechaexp(LocalDate fechaexp) {
        this.fechaexp = fechaexp;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getNvips() {
        return nvips;
    }

    public void setNvips(int nvips) {
        this.nvips = nvips;
    }

    public Parada getParadaInicial() {
        return paradaInicial;
    }

    public void setParadaInicial(Parada paradaInicial) {
        this.paradaInicial = paradaInicial;
    }

    public Peregrino getPropietario() {
        return propietario;
    }

    public void setPropietario(Peregrino propietario) {
        this.propietario = propietario;
    }
       
    
    // Metodos

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.fechaexp);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.distancia) ^ (Double.doubleToLongBits(this.distancia) >>> 32));
        hash = 23 * hash + this.nvips;
        hash = 23 * hash + Objects.hashCode(this.paradaInicial);
        hash = 23 * hash + Objects.hashCode(this.propietario);
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
        final Carnet other = (Carnet) obj;
        if (Double.doubleToLongBits(this.distancia) != Double.doubleToLongBits(other.distancia)) {
            return false;
        }
        if (this.nvips != other.nvips) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaexp, other.fechaexp)) {
            return false;
        }
        if (!Objects.equals(this.paradaInicial, other.paradaInicial)) {
            return false;
        }
        return Objects.equals(this.propietario, other.propietario);
    }

    @Override
    public String toString() {
        return "Carnet{" + "id=" + id + ", fechaexp=" + fechaexp + ", distancia=" + distancia + ", nvips=" + nvips + ", paradaInicial=" + paradaInicial + ", propietario=" + propietario + '}';
    }

    
    
    
}
