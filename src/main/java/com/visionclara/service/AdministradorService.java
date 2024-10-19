package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.Administrador;
import com.visionclara.repository.AdministradorRepository;



@Service
public class AdministradorService {
	@Autowired
	private AdministradorRepository repoAdministrador;
	
	// Agregar y actualizar
	public void save(Administrador admin) {
		repoAdministrador.save(admin);
	}
	
	// Listado Administradores
	public List<Administrador> listarAdministradores(){
		return repoAdministrador.findAll();
	}
	
	// Eliminar
	public void eliminarByID(int id) {
		repoAdministrador.deleteById(id);
	}
	
	// Listado x ID
	public Administrador listById(int id) {
		return repoAdministrador.findById(id).get();
	}

	// Buscar administrador en la transaccion
	public List<Administrador> listarAdministradorXApellido(String ape){
		return repoAdministrador.listApellidoAdministrador(ape);
	}

}
