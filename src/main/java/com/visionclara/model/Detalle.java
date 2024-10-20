package com.visionclara.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data

public class Detalle implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private int codigo;
	private int cantidad;
	private String nombre;
	private BigDecimal precio;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
