package com.visionclara.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "tb_administrador")
public class Administrador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_adm")
	private Integer codAdm;
	
	@Column(name = "nom_adm")
	private String nomAdm;

	@Column(name = "ape_adm")
	private String apeAdm;
	
	@Column(name = "dni_adm")
	private int dniAdm;
	
	@Column(name = "ema_adm")
	private String emaAdm;
	
	@Column(name = "tel_adm")
	private int telAdm;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fec_nac")
	private Date fecNac;
	
	@Column(name = "user_adm")
	private String userAdm;
	
	@Column(name = "clave")
	private String clave;
	
	@ManyToOne
	@JoinColumn(name = "cod_rol") // key foranea
	private Rol AdministradorRol; // 1.ASOC.

	@JsonIgnore
	@OneToMany(mappedBy = "compraAdministrador")
	private List<Compra> listaCompra;
	
	public List<Compra> getListaCompra() {
		return listaCompra;
	}

	public void setgetListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}


	public Administrador() {
		super();
	}

	public Administrador(Integer codAdm, String nomAdm, String apeAdm, int dniAdm, String emaAdm, int telAdm,
			Date fecNac, String userAdm, String clave, Rol administradorRol) {
		super();
		this.codAdm = codAdm;
		this.nomAdm = nomAdm;
		this.apeAdm = apeAdm;
		this.dniAdm = dniAdm;
		this.emaAdm = emaAdm;
		this.telAdm = telAdm;
		this.fecNac = fecNac;
		this.userAdm = userAdm;
		this.clave = clave;
		AdministradorRol = administradorRol;
	}
	
}
