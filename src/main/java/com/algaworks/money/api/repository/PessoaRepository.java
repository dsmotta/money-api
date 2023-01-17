package com.algaworks.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.money.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
