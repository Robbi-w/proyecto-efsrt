package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.CompraDetalle;
import com.visionclara.repository.CompraDetalleRepository;



@Service
public class CompraDetalleService {
	
	@Autowired
	private CompraDetalleRepository repoCompraDetalle;
	
	public List<CompraDetalle> listaDetalles(){
		return repoCompraDetalle.findAll();
	}
	
	public void eliminar(Integer cod) {
		repoCompraDetalle.deleteById(cod);
	}

}
