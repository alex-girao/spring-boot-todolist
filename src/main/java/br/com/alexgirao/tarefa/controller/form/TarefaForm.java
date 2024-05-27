package br.com.alexgirao.tarefa.controller.form;

import javax.validation.constraints.NotEmpty;

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
public class TarefaForm {
	
	@NotEmpty(message = "{tarefa.titulo.obrigatorio}")
	private String titulo;
	
	@NotEmpty(message = "{tarefa.descricao.obrigatoria}")
	private String descricao;
	
	@NotEmpty(message = "{tarefa.status.obrigatorio}")
	private String status;

}
