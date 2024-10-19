package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.Rol;
import com.visionclara.repository.RolRepository;




@Service
public class RolService {
	
		@Autowired
		private RolRepository rolRepo;
		
		// Listado de Usuarios
		public List<Rol> listarRoles() {
			return rolRepo.findAll();
		}

}
