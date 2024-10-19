package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.Marca;
import com.visionclara.repository.MarcaRepository;




@Service
public class MarcaService {
	
		@Autowired
		private MarcaRepository marcaRepo;
		
		// Listado de Marcas
		public List<Marca> listarMarcas(){
			return marcaRepo.findAll();
		}
		
		// Registra y Actualiza
		public void grabar(Marca bean) {
			marcaRepo.save(bean);
		}
		
		// buscar
		public Marca buscar(Integer cod) {
			return marcaRepo.findById(cod).orElse(null);
		}
		
		// eliminar
		public void eliminar(Integer cod) {
			marcaRepo.deleteById(cod);
		}
		
	
}
