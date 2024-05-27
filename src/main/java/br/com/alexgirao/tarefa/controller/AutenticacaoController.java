package br.com.alexgirao.tarefa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Alex Girao
 */
@RestController
@RequestMapping("/autenticacao")
@Api("Atenticação")
public class AutenticacaoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	@ApiOperation("Login")
    public TokenDTO login(@RequestBody LoginForm form){
        try{
            UserDetails usuarioAutenticado = usuarioService.autenticar(new Usuario(form.getEmail(), form.getSenha()));
            String token = jwtService.gerarToken(usuarioAutenticado);
            tokenService.renovarToken(usuarioAutenticado, token);
            return new TokenDTO(usuarioAutenticado.getUsername(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
	
	@PostMapping("/logout")
	@ApiOperation("Logout")
    public ResponseEntity<?> logout(Authentication authentication){
        try{
        	UsernamePasswordAuthenticationToken usuarioToken = (UsernamePasswordAuthenticationToken) authentication;
        	Usuario usuario = usuarioService.pesquisarPorEmail(usuarioToken.getName())
        			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        	tokenService.revogarToken(usuario);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
