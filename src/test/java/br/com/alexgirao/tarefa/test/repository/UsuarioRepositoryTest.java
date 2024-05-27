package br.com.alexgirao.tarefa.test.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.UsuarioRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@AfterEach
	public void afterEach() {
		usuarioRepository.deleteAll();
	}
	
	@Test
	public void createUsuario_WithValidData_ReturnsUsuario() {
		Usuario usuario = usuarioRepository.save(
			new Usuario("Usuario de Teste", "usuario.teste@email.com", "123")
		);
		Usuario sut = testEntityManager.find(Usuario.class, usuario.getId());
		assertThat(sut).isNotNull();
		assertThat(sut.getNome()).isEqualTo(usuario.getNome());
		assertThat(sut.getEmail()).isEqualTo(usuario.getEmail());
		assertThat(sut.getSenha()).isEqualTo(usuario.getSenha());
	}
	
}
