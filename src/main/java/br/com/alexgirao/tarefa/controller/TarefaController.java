package br.com.alexgirao.tarefa.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.alexgirao.tarefa.model.Tarefa;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.service.TarefaService;

/**
 * 
 * @author Alex Girao
 */
@RestController
@RequestMapping("/tarefa")
public class TarefaController {
	
	@Autowired
	private TarefaService tarefaService;
	
	@GetMapping
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
	public ResponseEntity<Response<TarefaDTO>> criar(@RequestBody @Valid TarefaForm form,
		Authentication authentication, UriComponentsBuilder uriBuilder) {
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		Response<TarefaDTO> response = new Response<>();
		try {
			Tarefa tarefa = tarefaService.criar(form.get(), usuario);
			response.setData(tarefaService.converter(tarefa));
			response.setMessage(Arrays.asList("Tarefa criada com sucesso."));
			URI uri = uriBuilder.path("/tarefa/").buildAndExpand(tarefa.getId()).toUri();
			return ResponseEntity.created(uri).body(response);
		}catch (Exception e) {
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<TarefaDTO>> atualizar(@PathVariable Long id, 
		@RequestBody @Valid TarefaForm form, Authentication authentication) {
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		Response<TarefaDTO> response = new Response<>();
		try {
			Optional<Tarefa> tarefa = tarefaService.pesquisarPorId(id);
			if(tarefa.isPresent()) {
				Tarefa atualizada = tarefaService.atualizar(id, form.get(), usuario);
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
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@DeleteMapping("/{id}")
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
