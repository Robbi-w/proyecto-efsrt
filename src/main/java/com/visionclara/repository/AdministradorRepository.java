package com.visionclara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.visionclara.model.Administrador;



@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{
	
		// Para registrar la compra
		@Query("select a from Administrador a where a.apeAdm like ?1")
		public List<Administrador> listApellidoAdministrador(String ape);
	
}
