package br.com.alexgirao.tarefa.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Alex Girao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "usuario", schema = "seguranca")
public class Usuario {
	
	@Id
	@SequenceGenerator(name = "usuario_id_seq", 
		sequenceName = "usuario_id_seq", allocationSize = 1, schema = "seguranca")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
	private Long id;
	
	@NotEmpty(message = "{usuario.nome.obrigatorio}")
	@Column(name = "nome", length = 255, nullable = false, unique = true)
	private String nome;
	
	@NotEmpty(message = "{usuario.email.obrigatorio}")
	@Column(name = "email", length = 255, nullable = false)
	private String email;
	
	@NotEmpty(message = "{usuario.senha.obrigatoria}")
	@Column(name = "senha", length = 255, nullable = false)
	private String senha;
	
	@OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
	private List<Tarefa> tarefas;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Token> tokens;
	
	public Usuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public Usuario(String nome, String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
}
