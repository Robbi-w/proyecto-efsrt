package com.visionclara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionclara.model.Marca;


@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer>{

}
