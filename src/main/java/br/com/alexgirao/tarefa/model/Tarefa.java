package br.com.alexgirao.tarefa.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import br.com.alexgirao.tarefa.enums.StatusEnum;
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
@Entity
@Table(name = "tarefa", schema = "public")
public class Tarefa {
	
	@Id
	@SequenceGenerator(name = "tarefa_id_seq", 
		sequenceName = "tarefa_id_seq", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarefa_id_seq")
	private Long id;
	
	@Column(name = "titulo", length = 120, nullable = false)
	private String titulo;

	@Column(name = "descricao", length = 255, nullable = false)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	@ColumnTransformer(write="?::status")
	private StatusEnum status;
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = true)
	private LocalDateTime dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_criacao", nullable = false)
	private Usuario usuarioCriacao;

	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = LocalDateTime.now();
	}

	@PrePersist
	public void prePersist() {
		dataCriacao = LocalDateTime.now();
	}

}
