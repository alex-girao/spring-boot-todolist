package br.com.alexgirao.tarefa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alexgirao.tarefa.enums.TokenTypeEnum;
import br.com.alexgirao.tarefa.model.Token;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.TokenRepository;

/**
 * 
 * @author Alex Girao
 */
@Service
public class TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Token criar(Usuario usuario, String jwt) {
		Token token = Token.builder()
			        .usuario(usuario)
			        .token(jwt)
			        .tokenType(TokenTypeEnum.BEARER)
			        .expired(false)
			        .revoked(false)
			        .build();
	    return tokenRepository.save(token);
	}
	
	public void desativarAntigos(Usuario usuario) {
		List<Token> validUserTokens = tokenRepository.findAllValidTokenByIdUsuario(usuario.getId());
	    if (validUserTokens.isEmpty()) {
	      return;
	    }
	    validUserTokens.forEach(token -> {
	      token.setExpired(true);
	      token.setRevoked(true);
	    });
	    tokenRepository.saveAll(validUserTokens);
	}

	public void renovarToken(UserDetails usuarioAutenticado, String token) {
		Optional<Usuario> usuario =	usuarioService.pesquisarPorEmail(usuarioAutenticado.getUsername());
		if(!usuario.isPresent()) {
			new UsernameNotFoundException("Usuário não encontrado");
		}
		desativarAntigos(usuario.get());
		criar(usuario.get(), token);
	}

	public void revogarToken(Usuario usuario) {
		Optional<Usuario> usuarioAtual = usuarioService.pesquisarPorEmail(usuario.getEmail());
		if(!usuarioAtual.isPresent()) {
			new UsernameNotFoundException("Usuário não encontrado");
		}
		desativarAntigos(usuario);
	}

	public boolean verificarAtivo(String token) {
		Optional<Token> tokenAtual = tokenRepository.findByToken(token);
		if(tokenAtual.isPresent()) {
			return !tokenAtual.get().isExpired() && !tokenAtual.get().isRevoked();
		}
		return false;
	}

}
