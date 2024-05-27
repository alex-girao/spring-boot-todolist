package br.com.alexgirao.tarefa.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alexgirao.tarefa.controller.UsuarioController;
import br.com.alexgirao.tarefa.controller.response.Response;
import br.com.alexgirao.tarefa.controller.response.ResponseStatusEnum;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.UsuarioRepository;
import br.com.alexgirao.tarefa.security.jwt.JwtService;
import br.com.alexgirao.tarefa.service.TokenService;
import br.com.alexgirao.tarefa.service.UsuarioService;

@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private TokenService tokenService;
	
	@Test
	public void creatUsuario_WithValidData_ReturnsCreated() throws Exception {
		
		Usuario usuario = new Usuario("Usuario de Teste 3", "usuario.teste3@email.com", "123");
		
		when(usuarioService.criar(usuario)).thenReturn(usuario);
		
		Response<?> responseSucess = new Response<>(ResponseStatusEnum.SUCCESS, null, Arrays.asList("Usuário criado com sucesso."));
		
		mockMvc.perform(
				post("/usuario")
					.content(objectMapper.writeValueAsString(usuario))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$").value(responseSucess));
	}
	
	@Test
	public void creatUsuario_WithInvalidData_ReturnsBadRequest() throws Exception {
		
		Usuario usuarioVazio = new Usuario();
		
		mockMvc.perform(
				post("/usuario")
					.content(objectMapper.writeValueAsString(usuarioVazio))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}

}
