package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionclara.model.Usuario;
import com.visionclara.repository.UsuarioRepository;



@Service
public class UsuarioService {
	
		@Autowired
		private UsuarioRepository usuarioRepo;
		// lListado de usuarios
		public List<Usuario> listarUsuarios() {
			return usuarioRepo.findAll();
		}
		// Guardar y Actualizar
		public void grabar(Usuario bean) {
			usuarioRepo.save(bean);
		}
		// Buscar
		public Usuario buscar(Integer cod) {
			return usuarioRepo.findById(cod).orElse(null);
		}
		// Eliminar
		public void eliminar(Integer cod) {
			usuarioRepo.deleteById(cod);
		}
		// Buscar Usuarios en la transaccion
		public List<Usuario> listarUsuarioXApellido(String ape){
			return usuarioRepo.listApellidoUsuario(ape);
		}
					
}
