package br.com.alexgirao.tarefa.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Alex Girao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDetalhadoDTO {
	
	private Long id;
	
	private String nome;
	
	private String email;
	
	private List<TarefaDTO> tarefas;

}
