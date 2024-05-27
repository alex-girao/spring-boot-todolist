package br.com.alexgirao.tarefa.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alexgirao.tarefa.controller.dto.TarefaDTO;
import br.com.alexgirao.tarefa.model.Tarefa;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.TarefaRepository;

/**
 * 
 * @author Alex Girao
 */
@Service
public class TarefaService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Tarefa criar(Tarefa tarefa, UsernamePasswordAuthenticationToken userToken) {
		Usuario usuario = usuarioService.pesquisarPorEmail(userToken.getName())
			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
		tarefa.setUsuarioCriacao(usuario);
		
		return tarefaRepository.save(tarefa);
	}
	
	public Tarefa criar(Tarefa tarefa, Usuario usuario) {
		tarefa.setUsuarioCriacao(usuario);
		return tarefaRepository.save(tarefa);
	}
	
	@Transactional
	public Tarefa atualizar(Long id, Tarefa tarefa) {
		Optional<Tarefa> tarefaAtual = tarefaRepository.findById(id);
		if (tarefaAtual.isPresent()) {
			tarefaAtual.get().setTitulo(tarefa.getTitulo());
			tarefaAtual.get().setDescricao(tarefa.getDescricao());
			tarefaAtual.get().setStatus(tarefa.getStatus());
		}
		return tarefaAtual.get();
	}
	
	public List<Tarefa> pesquisarTodos(){
		return tarefaRepository.findAll();
	}
	
	public List<TarefaDTO> pesquisarTodosDTO(){
		return converter(tarefaRepository.findAll());
	}
	
	public Optional<Tarefa> pesquisarPorId(Long id){
		return tarefaRepository.findById(id);
	}
	
	public Optional<TarefaDTO> pesquisarPorIdDTO(Long id){
		Optional<Tarefa> tarefa = tarefaRepository.findById(id);
		if(tarefa.isPresent()) {
			return Optional.of(converter(tarefa.get()));
		}
		return Optional.empty();
	}
	
	public void deletar(Long id) {
		tarefaRepository.deleteById(id);
	}
	
	public List<TarefaDTO> converter(List<Tarefa> list) {
		if (list != null) {
			Type type = new TypeToken<List<TarefaDTO>>() {
			}.getType();
			List<TarefaDTO> listDTO = modelMapper.map(list, type);
			return listDTO;
		}
		return null;
	}

	public TarefaDTO converter(Tarefa tarefa) {
		return modelMapper.map(tarefa, TarefaDTO.class);
	}


}
