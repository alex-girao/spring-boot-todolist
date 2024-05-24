package br.com.alexgirao.tarefa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.alexgirao.tarefa.enums.TokenTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Alex Girao
 */
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "token", schema = "seguranca")
public class Token {

	@Id
	@SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1, schema = "seguranca")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_seq")
	private Long id;

	@Column(unique = true)
	public String token;

	@Enumerated(EnumType.STRING)
	public TokenTypeEnum tokenType;

	public boolean revoked;

	public boolean expired;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	public Usuario usuario;

	public Token() {
		tokenType = TokenTypeEnum.BEARER;
	}

}
