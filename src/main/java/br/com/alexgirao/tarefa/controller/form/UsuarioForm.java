package br.com.alexgirao.tarefa.controller.form;

import javax.validation.constraints.Email;
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
public class UsuarioForm {
	
	@NotEmpty(message = "{usuario.nome.obrigatorio}")
	private String nome;
	
	@NotEmpty(message = "{usuario.email.obrigatorio}")
	@Email(message = "{usuario.email.invalido}")
	private String email;
	
	@NotEmpty(message = "{usuario.senha.obrigatoria}")
	private String senha;

	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		return usuario;
	}

}
