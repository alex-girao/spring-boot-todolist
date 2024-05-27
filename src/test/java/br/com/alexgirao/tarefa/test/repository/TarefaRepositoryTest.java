package br.com.alexgirao.tarefa.test.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import br.com.alexgirao.tarefa.enums.StatusEnum;
import br.com.alexgirao.tarefa.model.Tarefa;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.TarefaRepository;
import br.com.alexgirao.tarefa.repository.UsuarioRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TarefaRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@AfterEach
	public void afterEach() {
		tarefaRepository.deleteAll();
		usuarioRepository.deleteAll();
	}
	
	@Test
	public void creatTarefa_WithValidData_ReturnTarefa() throws Exception {
		
		Usuario usuario = usuarioRepository.save(
			new Usuario("Usuario de Teste", "usuario.teste@email.com", "123")
		);
		
		Tarefa tarefa = tarefaRepository.save(
			new Tarefa("Tarefa de Teste", "Rotina de teste da aplicacao", 
				StatusEnum.PENDENTE, usuario)
		);
		
		Tarefa sut = testEntityManager.find(Tarefa.class, tarefa.getId());
		
		assertThat(sut).isNotNull();
		assertThat(sut.getTitulo()).isEqualTo(tarefa.getTitulo());
		assertThat(sut.getDescricao()).isEqualTo(tarefa.getDescricao());
		assertThat(sut.getStatus()).isEqualTo(tarefa.getStatus());
	}
	
	@Test
	public void deleteTarefa_ById() throws Exception {
		
		Usuario usuario = usuarioRepository.save(
			new Usuario("Usuario de Teste 3", "usuario.teste3@email.com", "123")
		);
		
		Tarefa tarefa = tarefaRepository.save(
			new Tarefa("Tarefa de Teste 3", "Rotina de teste da aplicacao 3", 
				StatusEnum.CONCLUIDO, usuario)
		);
		
		tarefaRepository.deleteById(tarefa.getId());
		
		Tarefa sut = testEntityManager.find(Tarefa.class, tarefa.getId());
		
		assertThat(sut).isNull();
	}
	
	@Test
	public void getTarefa_ByUnexistingId() throws Exception {
		Optional<Tarefa> sut = tarefaRepository.findById(99L);
		assertThat(sut).isEqualTo(Optional.empty());
	}
	
	@Test
	public void getTarefa_ByExistingId() throws Exception {
		
		Usuario usuario = usuarioRepository.save(
			new Usuario("Usuario de Teste 4", "usuario.teste4@email.com", "123")
		);
		
		Tarefa tarefa = tarefaRepository.save(
			new Tarefa("Tarefa de Teste 4", "Rotina de teste da aplicacao 4", 
				StatusEnum.CONCLUIDO, usuario)
		);
		
		
		Optional<Tarefa> sut = tarefaRepository.findById(tarefa.getId());
		assertThat(sut.get()).isNotNull();
	}
	
	@Test
	public void updateTarefa_WithValidData() throws Exception {
		Usuario usuario = usuarioRepository.save(
			new Usuario("Usuario de Teste 5", "usuario.teste5@email.com", "123")
		);
		Tarefa tarefa = tarefaRepository.save(
			new Tarefa("Tarefa de Teste 5", "Rotina de teste da aplicacao 5", 
				StatusEnum.PENDENTE, usuario)
		);
		
		Optional<Tarefa> tarefaUpdate = tarefaRepository.findById(tarefa.getId());
		tarefaUpdate.get().setTitulo("Tarefa de Teste Cinco");
		tarefaUpdate.get().setDescricao("Rotina de teste da aplicacao Cinco");
		tarefaUpdate.get().setStatus(StatusEnum.CONCLUIDO);
		tarefaRepository.save(tarefaUpdate.get());
		
		Tarefa sut = testEntityManager.find(Tarefa.class, tarefa.getId());
		
		assertThat(sut).isNotNull();
		assertThat(sut.getTitulo()).isEqualTo(tarefa.getTitulo());
		assertThat(sut.getDescricao()).isEqualTo(tarefa.getDescricao());
		assertThat(sut.getStatus()).isEqualTo(tarefa.getStatus());
	}

}
