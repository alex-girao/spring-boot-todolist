package br.com.alexgirao.tarefa.controller.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Alex Girao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaDTO {
	
	private Long id;
	private String titulo;
	private String descricao;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataAtualizacao;
	private UsuarioDTO usuarioCriacao;

}
