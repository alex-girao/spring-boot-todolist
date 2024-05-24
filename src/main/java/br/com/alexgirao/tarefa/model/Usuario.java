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
	
	@Column(name = "nome", length = 255, nullable = false)
	private String nome;
	
	@Column(name = "email", length = 255, nullable = false)
	private String email;
	
	@Column(name = "senha", length = 255, nullable = false)
	private String senha;
	
	@OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
	private List<Tarefa> tarefas;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Token> tokens;


}
