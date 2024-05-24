package br.com.alexgirao.tarefa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alexgirao.tarefa.model.Tarefa;

/**
 * 
 * @author Alex Girao
 */
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

}
