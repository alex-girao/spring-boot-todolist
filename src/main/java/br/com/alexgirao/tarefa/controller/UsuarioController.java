package br.com.alexgirao.tarefa.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alexgirao.tarefa.controller.dto.UsuarioDTO;
import br.com.alexgirao.tarefa.controller.dto.UsuarioDetalhadoDTO;
import br.com.alexgirao.tarefa.controller.form.UsuarioForm;
import br.com.alexgirao.tarefa.controller.response.Response;
import br.com.alexgirao.tarefa.controller.response.ResponseStatusEnum;
import br.com.alexgirao.tarefa.exception.EmailExistenteException;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.service.UsuarioService;

/**
 * 
 * @author Alex Girao
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> criar(@RequestBody @Valid UsuarioForm form,
		UriComponentsBuilder uriBuilder) {
		Response<UsuarioDTO> response = new Response<>();
		try {
			Usuario usuario = usuarioService.criar(form.getUsuario());
			URI uri = uriBuilder.path("/usuario/").buildAndExpand(usuario.getId()).toUri();
			response.setData(usuarioService.converter(usuario));
			response.setMessage(Arrays.asList("Usuário criado com sucesso."));
			return ResponseEntity.created(uri).body(response);
		}catch (EmailExistenteException e) {
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList(e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}catch (Exception e) {
			response.setStatus(ResponseStatusEnum.ERROR);
			response.setMessage(Arrays.asList("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<UsuarioDetalhadoDTO>> obter(@PathVariable Long id) {
		Response<UsuarioDetalhadoDTO> response = new Response<>();
		try {
			Optional<UsuarioDetalhadoDTO> usuario = usuarioService.pesquisarPorIdDetalhado(id);
			if(usuario.isPresent()) {
				response.setStatus(ResponseStatusEnum.SUCCESS);
				response.setData(usuario.get());
				return ResponseEntity.ok(response);
			} else {
				response.setMessage(Arrays.asList("Usuário não encontrado."));
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

}
