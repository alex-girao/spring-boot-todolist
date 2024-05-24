package br.com.alexgirao.tarefa.exception;

/**
 * 
 * @author Alex Girao
 */
public class SenhaInvalidaException extends RuntimeException {
	
    private static final long serialVersionUID = 2433391469380332611L;

	public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
