package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.Proveedor;
import com.visionclara.repository.ProveedorRepository;


@Service
public class ProveedorService {
	
		@Autowired
		private ProveedorRepository ProveedorRepo;

		// Listado de proveedores
		public List<Proveedor> listarProveedores() {
			return ProveedorRepo.findAll();
		}

		// Registro y actualizacion
		public void grabar(Proveedor bean) {
			ProveedorRepo.save(bean);
		}

		// Buscar
		public Proveedor buscar(Integer cod) {
			return ProveedorRepo.findById(cod).orElse(null);
		}

		// Eliminar
		public void eliminar(Integer cod) {
			ProveedorRepo.deleteById(cod);
		}
		
	
}
