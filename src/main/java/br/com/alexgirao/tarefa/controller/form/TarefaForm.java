package br.com.alexgirao.tarefa.controller.form;

import javax.validation.constraints.NotEmpty;

import br.com.alexgirao.tarefa.enums.StatusEnum;
import br.com.alexgirao.tarefa.model.Tarefa;
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
	
	public Tarefa get() {
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo(titulo);
		tarefa.setDescricao(descricao);
		tarefa.setStatus(StatusEnum.valueOf(status));
		return tarefa;
	}

}
