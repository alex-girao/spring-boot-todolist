package br.com.alexgirao.tarefa.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alexgirao.tarefa.controller.TarefaController;
import br.com.alexgirao.tarefa.controller.form.TarefaForm;
import br.com.alexgirao.tarefa.enums.StatusEnum;
import br.com.alexgirao.tarefa.model.Tarefa;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.UsuarioRepository;
import br.com.alexgirao.tarefa.security.jwt.JwtService;
import br.com.alexgirao.tarefa.service.TarefaService;
import br.com.alexgirao.tarefa.service.TokenService;
import br.com.alexgirao.tarefa.service.UsuarioService;

@WebMvcTest(controllers = TarefaController.class)
public class TarefaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private TarefaService tarefaService;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private TokenService tokenService;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void creatTarefa_WithValidData_ReturnsCreated() throws Exception {
		
		TarefaForm tarefa = new TarefaForm("Tarefa de Teste", "Rotina de teste da aplicacao", StatusEnum.PENDENTE.name());
		
		mockMvc.perform(
				post("/tarefa")
				.with(SecurityMockMvcRequestPostProcessors.user("usuario.teste4@email.com"))
					.content(objectMapper.writeValueAsString(tarefa))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated());
	}
	
	@Test
	public void creatTarefa_WithInvalidData_ReturnBadRequest() throws Exception {
		
		TarefaForm tarefaVazia = new TarefaForm();
		
		mockMvc.perform(
				post("/tarefa")
				.with(SecurityMockMvcRequestPostProcessors.user("usuario.teste4@email.com"))
					.content(objectMapper.writeValueAsString(tarefaVazia))
					.contentType(MediaType.APPLICATION_JSON)
			)
		.andExpect(status().isBadRequest())
		;
		
	}
	
	@Test
	public void getTarefa_ByUnexistingId_ReturnNotFound() throws Exception {
		mockMvc.perform(
				get("/tarefa/99")
				.with(SecurityMockMvcRequestPostProcessors.user("usuario.teste4@email.com"))
			)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getTarefa_ByExistingId_ReturnTarefa() throws Exception {
		
		Usuario usuario = new Usuario("Usuario de Teste 3", "usuario.teste3@email.com", "123");
		when(usuarioService.criar(usuario)).thenReturn(usuario);
		
		when(tarefaService.pesquisarPorId(1L)).thenReturn(Optional.of(new Tarefa(1L, "Tarefa de Teste Atualizada", "Rotina de teste da aplicacao atualizada", StatusEnum.CONCLUIDO)));
		
		mockMvc.perform(
				get("/tarefa/2")
				.with(SecurityMockMvcRequestPostProcessors.user("usuario.teste4@email.com"))
			)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateTarefa_WithValidData_ReturnsOk() throws Exception {
		when(tarefaService.pesquisarPorId(1L)).thenReturn(Optional.of(new Tarefa(1L, "Tarefa de Teste Atualizada", "Rotina de teste da aplicacao atualizada", StatusEnum.CONCLUIDO)));
		
		Tarefa tarefa = new Tarefa("Tarefa de Teste Atualizada", "Rotina de teste da aplicacao atualizada", StatusEnum.CONCLUIDO);
		
		mockMvc.perform(
				put("/tarefa/1")
				.with(SecurityMockMvcRequestPostProcessors.user("usuario.teste4@email.com"))
					.content(objectMapper.writeValueAsString(tarefa))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteTarefa_ReturnsOk() throws Exception {
		when(tarefaService.pesquisarPorId(11L)).thenReturn(Optional.of(new Tarefa(11L, "Tarefa de Teste Atualizada", "Rotina de teste da aplicacao atualizada", StatusEnum.CONCLUIDO)));
		
		Tarefa tarefa = new Tarefa("Tarefa de Teste Atualizada", "Rotina de teste da aplicacao atualizada", StatusEnum.CONCLUIDO);
		
		mockMvc.perform(
				delete("/tarefa/11")
				.with(SecurityMockMvcRequestPostProcessors.user("usuario.teste4@email.com"))
					.content(objectMapper.writeValueAsString(tarefa))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk());
	}
	

}
