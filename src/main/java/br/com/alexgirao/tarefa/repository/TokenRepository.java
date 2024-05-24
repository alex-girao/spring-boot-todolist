package br.com.alexgirao.tarefa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alexgirao.tarefa.model.Token;

/**
 * 
 * @author Alex Girao
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

	@Query(value = "select t from Token t "
		+ "inner join Usuario u on t.usuario.id = u.id "
		+ "where u.id = :idUsuario "
		+ "and (t.expired = false or t.revoked = false)")
	List<Token> findAllValidTokenByIdUsuario(Long idUsuario);

	Optional<Token> findByToken(String token);

}
