package br.com.alexgirao.tarefa.controller.dto;

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
public class TokenDTO {
    private String login;
    private String token;
}
