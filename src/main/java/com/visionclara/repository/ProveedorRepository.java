package com.visionclara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionclara.model.Proveedor;


@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

}
