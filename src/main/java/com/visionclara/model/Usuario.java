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

}
