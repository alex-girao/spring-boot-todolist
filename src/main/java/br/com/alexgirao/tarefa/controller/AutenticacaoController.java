package br.com.alexgirao.tarefa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.alexgirao.tarefa.controller.dto.TokenDTO;
import br.com.alexgirao.tarefa.controller.form.LoginForm;
import br.com.alexgirao.tarefa.exception.SenhaInvalidaException;
import br.com.alexgirao.tarefa.model.Usuario;
import br.com.alexgirao.tarefa.security.jwt.JwtService;
import br.com.alexgirao.tarefa.service.TokenService;
import br.com.alexgirao.tarefa.service.UsuarioService;

/**
 * 
 * @author Alex Girao
 */
@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
    public TokenDTO login(@RequestBody LoginForm form){
        try{
            UserDetails usuarioAutenticado = usuarioService.autenticar(form.getUsuario());
            String token = jwtService.gerarToken(usuarioAutenticado);
            tokenService.renovarToken(usuarioAutenticado, token);
            return new TokenDTO(usuarioAutenticado.getUsername(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
	
	@PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication){
        try{
        	Usuario usuario = (Usuario) authentication.getPrincipal();
        	tokenService.revogarToken(usuario);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
