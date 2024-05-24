package br.com.alexgirao.tarefa.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alexgirao.tarefa.controller.dto.UsuarioDTO;
import br.com.alexgirao.tarefa.controller.dto.UsuarioDetalhadoDTO;
import br.com.alexgirao.tarefa.exception.EmailExistenteException;
import br.com.alexgirao.tarefa.exception.SenhaInvalidaException;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	public Usuario criar(Usuario usuario) {
		if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			throw new EmailExistenteException("E-mail já cadastrado");
		}
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	public UsuarioDTO converter(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
		String[] roles = new String[]{"USER"};
		
		return User
				.builder()
				.username(usuario.getEmail())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

	public Optional<UsuarioDetalhadoDTO> pesquisarPorIdDetalhado(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		if(usuario.isPresent()) {
			return Optional.of(modelMapper.map(usuario.get(), UsuarioDetalhadoDTO.class));
		}
		
		return Optional.empty();
	}
	
	public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword() );

        if(senhasBatem){
            return user;
        }
        
        throw new SenhaInvalidaException();
    }

	public Optional<Usuario> pesquisarPorEmail(String username) {
		return usuarioRepository.findByEmail(username);
	}

}
