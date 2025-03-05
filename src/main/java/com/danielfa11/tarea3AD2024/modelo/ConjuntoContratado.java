package com.danielfa11.tarea3AD2024.modelo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ConjuntoContratado {

	private Long id;
	private double precioTotal;
	private char modoPago;
	private String extra = null;
	
	private Set<Long> servicios = new HashSet<>();

	// Constructores
	
	public ConjuntoContratado(Long id, double precioTotal, char modoPago, String extra) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
	}

	public ConjuntoContratado() {
		super();
	}

	// Getters y Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public char getModoPago() {
		return modoPago;
	}

	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Set<Long> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Long> servicios) {
		this.servicios = servicios;
	}

	// Metodods
	
	@Override
	public int hashCode() {
		return Objects.hash(extra, id, modoPago, precioTotal, servicios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjuntoContratado other = (ConjuntoContratado) obj;
		return Objects.equals(extra, other.extra) && Objects.equals(id, other.id) && modoPago == other.modoPago
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal)
				&& Objects.equals(servicios, other.servicios);
	}

	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + ", servicios=" + servicios + "]";
	}
	
	
	
}
