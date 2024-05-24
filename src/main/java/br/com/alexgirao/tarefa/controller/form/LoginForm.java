package br.com.alexgirao.tarefa.controller.form;

import javax.validation.constraints.NotEmpty;

import br.com.alexgirao.tarefa.model.Usuario;
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
public class LoginForm {
	
	@NotEmpty(message = "{login.email.obrigatorio}")
	private String email;
	
	@NotEmpty(message = "{login.senha.obrigatoria}")
	private String senha;

	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setSenha(senha);
		return usuario;
	}

}
