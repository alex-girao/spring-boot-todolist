package br.com.alexgirao.tarefa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alexgirao.tarefa.model.Usuario;

/**
 * 
 * @author Alex Girao
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
