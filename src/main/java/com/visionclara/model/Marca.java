package com.visionclara.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_marca")
public class Marca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_marca") private Integer codMarca;
	
	@Column(name = "nom_marca") private String nomMarca;

	@Column(name = "pai_marca") private String paiMarca;
	
	//Ignora json para buscar por cod(update)
	@JsonIgnore
	@OneToMany(mappedBy = "productoMarca") 
	private List<Producto> listaProductos;

	public Integer getCodMarca() {
		return codMarca;
	}

	public void setCodMarca(Integer codMarca) {
		this.codMarca = codMarca;
	}

	public String getNomMarca() {
		return nomMarca;
	}

	public void setNomMarca(String nomMarca) {
		this.nomMarca = nomMarca;
	}

	public String getPaiMarca() {
		return paiMarca;
	}

	public void setPaiMarca(String paiMarca) {
		this.paiMarca = paiMarca;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
}
