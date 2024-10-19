package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.Producto;
import com.visionclara.repository.ProductoRepository;



@Service
public class ProductoService {
	
		@Autowired
		private ProductoRepository productoRepo;
		
		// Listado de productos
		public List<Producto> listarProductos() {
			return productoRepo.findAll();
		}

		// Registrar y Actualizar
		public void grabar(Producto bean) {
			productoRepo.save(bean);
		}

		// Buscar 
		public Producto buscar(Integer cod) {
			return productoRepo.findById(cod).orElse(null);
		}

		// Eliminar
		public void eliminar(Integer cod) {
			productoRepo.deleteById(cod);
		}
		
		// Listado x ID
		public Producto listById(int id) {
			return productoRepo.findById(id).get();
		}
		
		// Update de imagen
		public void actualizarImg(byte[] img, String nomAr, Integer cod) {
			productoRepo.actualizarImagen(img, nomAr, cod);
		}
	
}
