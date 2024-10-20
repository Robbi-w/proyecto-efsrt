package com.visionclara.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_producto")
public class Producto {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prod") private Integer codProd;
	
	@Column(name = "nom_prod") private String nomProd;
	
	@Column(name = "des_prod") private String desProd;
	
	@Column(name = "cat_prod") private String catProd;
	
	@Column(name = "stock_prod") private int stockProd;
	
	@Column(name = "precio_compra") private BigDecimal precioCompra;
	
	// Sin nombre de columna
	private byte[] img_prod;
	
	@Column(name = "nom_archivo") private String nomArchivo;

	@ManyToOne
	@JoinColumn(name = "cod_marca")
	private Marca productoMarca;

	@ManyToOne
	@JoinColumn(name = "cod_prov")
	private Proveedor productoProveedor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "CDetalleProducto")
	private List<CompraDetalle> listaCDetalle;

	public List<CompraDetalle> getListaCDetalle() {
		return listaCDetalle;
	}

	public void setListaCDetalle(List<CompraDetalle> listaCDetalle) {
		this.listaCDetalle = listaCDetalle;
	}
	
	public String getNomArchivo() {
		return nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public Integer getCodProd() {
		return codProd;
	}

	public void setCodProd(Integer codProd) {
		this.codProd = codProd;
	}

	public String getNomProd() {
		return nomProd;
	}

	public void setNomProd(String nomProd) {
		this.nomProd = nomProd;
	}

	public String getDesProd() {
		return desProd;
	}

	public void setDesProd(String desProd) {
		this.desProd = desProd;
	}

	public String getCatProd() {
		return catProd;
	}

	public void setCatProd(String catProd) {
		this.catProd = catProd;
	}

	public int getStockProd() {
		return stockProd;
	}

	public void setStockProd(int stockProd) {
		this.stockProd = stockProd;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public byte[] getImg_prod() {
		return img_prod;
	}

	public void setImg_prod(byte[] img_prod) {
		this.img_prod = img_prod;
	}

	public Marca getProductoMarca() {
		return productoMarca;
	}

	public void setProductoMarca(Marca productoMarca) {
		this.productoMarca = productoMarca;
	}

	public Proveedor getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(Proveedor productoProveedor) {
		this.productoProveedor = productoProveedor;
	}
	
}
