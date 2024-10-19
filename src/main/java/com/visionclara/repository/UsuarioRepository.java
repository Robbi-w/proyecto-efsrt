package com.visionclara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.visionclara.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
    @Query("select u from Usuario u where u.apeUsu like ?1")
	public List<Usuario> listApellidoUsuario(String ape);
	
	
}
