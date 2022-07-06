package com.algaworks.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.money.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	

}
