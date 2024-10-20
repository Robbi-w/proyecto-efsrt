package com.visionclara.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_detalle_venta")
public class CompraDetalle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_det_venta") private Integer codDetCom;
	
	@Column(name = "precio") private BigDecimal precio;
	
	@Column(name = "cantidad") private int cantidad;
	
	@ManyToOne
	@JoinColumn(name = "cod_prod") private Producto CDetalleProducto;
	
	@ManyToOne
	@JoinColumn(name ="cod_venta") private Compra CDetalleCompra;
	
	public Compra getCDetalleCompra() {
		return CDetalleCompra;
	}

	public void setCDetalleCompra(Compra cDetalleCompra) {
		CDetalleCompra = cDetalleCompra;
	}

	public Integer getCodDetCom() {
		return codDetCom;
	}

	public void setCodDetCom(Integer codDetCom) {
		this.codDetCom = codDetCom;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getCDetalleProducto() {
		return CDetalleProducto;
	}

	public void setCDetalleProducto(Producto cDetalleProducto) {
		CDetalleProducto = cDetalleProducto;
	}
	
	

}
