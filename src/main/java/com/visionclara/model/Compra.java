package com.visionclara.model;

import java.math.BigDecimal;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_venta")
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_venta") private Integer codCom;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fec_venta") private Date fecCom;
	
	@Column(name = "monto_venta") private BigDecimal montoCom;	
	
	@Column(name = "mpago")private String mpago;
	
	@ManyToOne
	@JoinColumn(name = "cod_usu") private Usuario compraUsuario; 
	
	@ManyToOne
	@JoinColumn(name = "cod_adm") private Administrador compraAdministrador;
	
	@JsonIgnore
	@OneToMany(mappedBy = "CDetalleCompra") private List<CompraDetalle> listaCDetalle;
	
	public List<CompraDetalle> getListaCDetalle() {
		return listaCDetalle;
	}

	public void setListaCDetalle(List<CompraDetalle> listaCDetalle) {
		this.listaCDetalle = listaCDetalle;
	}

	public Integer getCodCom() {
		return codCom;
	}

	public void setCodCom(Integer codCom) {
		this.codCom = codCom;
	}

	public Date getFecCom() {
		return fecCom;
	}

	public void setFecCom(Date fecCom) {
		this.fecCom = fecCom;
	}

	public BigDecimal getMontoCom() {
		return montoCom;
	}

	public void setMontoCom(BigDecimal montoCom) {
		this.montoCom = montoCom;
	}

	public String getMpago() {
		return mpago;
	}

	public void setMpago(String mpago) {
		this.mpago = mpago;
	}

	public Usuario getCompraUsuario() {
		return compraUsuario;
	}

	public void setCompraUsuario(Usuario compraUsuario) {
		this.compraUsuario = compraUsuario;
	}

	public Administrador getCompraAdministrador() {
		return compraAdministrador;
	}

	public void setCompraAdministrador(Administrador compraAdministrador) {
		this.compraAdministrador = compraAdministrador;
	}
	
}
