package com.algaworks.money.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.money.api.event.RecursoCriadoEvent;
import com.algaworks.money.api.model.Pessoa;
import com.algaworks.money.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listaTodasPessoas(){
		
		return pessoaRepository.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<Pessoa> salvaPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa); //SALVA OBJETO PESSOA 
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva); //RETORNA PARA O NAVEGADOR E NO BODY A PESSOA SALVA
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscaPessoaPorId(@PathVariable Long id){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // retorno 204 ou seja consegue realizar a exclusaão mas não retorna nada
	public void remover(@PathVariable Long id) {
		
		pessoaRepository.deleteById(id);
	}
	

}
