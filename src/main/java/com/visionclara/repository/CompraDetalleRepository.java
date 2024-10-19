package com.visionclara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionclara.model.CompraDetalle;


@Repository
public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Integer>{

}
