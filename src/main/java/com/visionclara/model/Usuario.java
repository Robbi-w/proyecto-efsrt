package com.visionclara.model;

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
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_usu") private Integer codUsu;
	
	@Column(name = "nom_usu") private String nomUsu;

	@Column(name = "ape_usu") private String apeUsu;
	
	@Column(name = "dni_usu") private int dniUsu;
	
	@Column(name = "ema_usu") private String emaUsu;
	
	@Column(name = "tel_usu") private int telUsu;
	
	@Column(name = "dir_usu") private String dirUsu;
	
	@ManyToOne
	@JoinColumn(name = "cod_rol")
	private Rol usuarioRol;
	
	@JsonIgnore
	@OneToMany(mappedBy = "compraUsuario")
	private List<Compra> listaCompra;

	public Integer getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(Integer codUsu) {
		this.codUsu = codUsu;
	}

	public String getNomUsu() {
		return nomUsu;
	}

	public void setNomUsu(String nomUsu) {
		this.nomUsu = nomUsu;
	}

	public String getApeUsu() {
		return apeUsu;
	}

	public void setApeUsu(String apeUsu) {
		this.apeUsu = apeUsu;
	}

	public int getDniUsu() {
		return dniUsu;
	}

	public void setDniUsu(int dniUsu) {
		this.dniUsu = dniUsu;
	}

	public String getEmaUsu() {
		return emaUsu;
	}

	public void setEmaUsu(String emaUsu) {
		this.emaUsu = emaUsu;
	}

	public int getTelUsu() {
		return telUsu;
	}

	public void setTelUsu(int telUsu) {
		this.telUsu = telUsu;
	}

	public String getDirUsu() {
		return dirUsu;
	}

	public void setDirUsu(String dirUsu) {
		this.dirUsu = dirUsu;
	}

	public Rol getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(Rol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

	public List<Compra> getListaCompra() {
		return listaCompra;
	}

	public void setListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}
	
	

}
