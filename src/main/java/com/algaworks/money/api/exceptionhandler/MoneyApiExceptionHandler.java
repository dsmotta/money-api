package com.algaworks.money.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MoneyApiExceptionHandler extends ResponseEntityExceptionHandler { //classe responsavel pela captura de excessoes

	@Autowired
	private MessageSource messageSource; //Usado para poder pegar a manesagem do message.properties
	
	//Metodo da extensão para erro de leitura
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale()); //variavel que contem a mensagem de excessão do usuario no message.properties
		String mensagemDesenvolvedor = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex,erros , headers, status.BAD_REQUEST, request); //retorno do metodo de excessão do usuario
	}
	
	//Metodo que capta excessoes de argumentos não validados ou seja Nulos
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaErros(ex.getBindingResult()); //PEGA TODAS AS EXCESSOES PASSANDO PARAMETRO PARA O METODO CRIAR A LISTA
		
		return handleExceptionInternal(ex, erros , headers, HttpStatus.BAD_REQUEST, request); //RETORNO DA LISTA DE EXCESSOES COM O STATUS
	}
	
	private List<Erro> criarListaErros(BindingResult bindingResult){ //METODO PARA CRIAR A LISTA COM AS EXCESSOES
		
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) { //FOR PARA VARRER O OBJETO E PEGAR TODAS AS EXCESSOES
		
			String mensagemUsusarioString = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()); 
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsusarioString, mensagemDesenvolvedor));
		
		}
		
		return erros;
	}
	
	
	//Criando uma classe dentro da classe de excessão para poder ser usada no retorno as mensagnes  para o usuario e para o desenvolvedor
	public static class Erro{ //criando uma classe para poder dar um retorno com as duas mensagens
		
		private String mensagemUsuario; //cariação das variaveis
		private String mensaemDesenvolvedor;
		
		
		public Erro(String mensagemUsuario, String mensaemDesenvolvedor) { //construtor 
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensaemDesenvolvedor = mensaemDesenvolvedor;
		}


		public String getMensagemUsuario() {
			return mensagemUsuario;
		}


		public String getMensaemDesenvolvedor() {
			return mensaemDesenvolvedor;
		}
		
		
		
		
		
	}
	
	
}
