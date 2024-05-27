package br.com.alexgirao.tarefa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alexgirao.tarefa.controller.dto.TarefaDTO;
import br.com.alexgirao.tarefa.controller.form.TarefaForm;
import br.com.alexgirao.tarefa.controller.response.Response;
import br.com.alexgirao.tarefa.controller.response.ResponseStatusEnum;
import br.com.alexgirao.tarefa.enums.StatusEnum;
import br.com.alexgirao.tarefa.model.Tarefa;
import br.com.alexgirao.tarefa.service.TarefaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Alex Girao
 */
@RestController
@RequestMapping("/tarefa")
@Api("Tarefa")
public class TarefaController {
	
	@Autowired
	private TarefaService tarefaService;
	
	@GetMapping
	@ApiOperation("Lista todas as tarefas")
	public ResponseEntity<Response<List<TarefaDTO>>> listar(){
		Response<List<TarefaDTO>> response = new Response<>();
		try {
			response.setStatus(ResponseStatusEnum.SUCCESS);
			response.setData(tarefaService.pesquisarTodosDTO());
			return ResponseEntity.ok(response);
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Listar tarefa por Id")
	public ResponseEntity<Response<TarefaDTO>> obter(@PathVariable Long id) {
		Response<TarefaDTO> response = new Response<>();
		try {
			Optional<TarefaDTO> tarefa = tarefaService.pesquisarPorIdDTO(id);
			if(tarefa.isPresent()) {
				response.setStatus(ResponseStatusEnum.SUCCESS);
				response.setData(tarefa.get());
				return ResponseEntity.ok(response);
			} else {
				response.setMessage(Arrays.asList("Tarefa não encontrada."));
				response.setStatus(ResponseStatusEnum.WARNING);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping
	@ApiOperation("Criar tarefa")
	public ResponseEntity<Response<TarefaDTO>> criar(@RequestBody @Valid TarefaForm form,
		Authentication authentication, UriComponentsBuilder uriBuilder) {
		
		Response<TarefaDTO> response = new Response<>();
		try {
			Tarefa tarefa = tarefaService.criar(
				new Tarefa(form.getTitulo(), form.getDescricao(), StatusEnum.valueOf(form.getStatus())), 
				(UsernamePasswordAuthenticationToken) authentication);
			response.setData(tarefaService.converter(tarefa));
			response.setMessage(Arrays.asList("Tarefa criada com sucesso."));
			response.setStatus(ResponseStatusEnum.SUCCESS);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PutMapping("/{id}")
	@ApiOperation("Atualizar tarefa por Id")
	public ResponseEntity<Response<TarefaDTO>> atualizar(@PathVariable Long id, 
		@RequestBody @Valid TarefaForm form) {
		
		Response<TarefaDTO> response = new Response<>();
		try {
			Optional<Tarefa> tarefa = tarefaService.pesquisarPorId(id);
			if(tarefa.isPresent()) {
				Tarefa atualizada = tarefaService.atualizar(id, 
					new Tarefa(form.getTitulo(), form.getDescricao(), StatusEnum.valueOf(form.getStatus())));
				response.setData(tarefaService.converter(atualizada));
				response.setMessage(Arrays.asList("Tarefa alterada com sucesso."));
				response.setStatus(ResponseStatusEnum.SUCCESS);
				return ResponseEntity.ok(response);
			} else {
				response.setMessage(Arrays.asList("Tarefa não encontrada."));
				response.setStatus(ResponseStatusEnum.WARNING);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}	
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Remover tarefa por Id")
	public ResponseEntity<Response<?>> deletar(@PathVariable Long id) {
		Response<TarefaDTO> response = new Response<>();
		try {
			Optional<Tarefa> tarefa = tarefaService.pesquisarPorId(id);
			if(tarefa.isPresent()) {
				tarefaService.deletar(id);
				response.setMessage(Arrays.asList("Tarefa removida com sucesso."));
				response.setStatus(ResponseStatusEnum.SUCCESS);
				return ResponseEntity.ok(response);
			} else {
				response.setMessage(Arrays.asList("Tarefa não encontrada."));
				response.setStatus(ResponseStatusEnum.WARNING);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}	
		}catch (Exception e) {
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
