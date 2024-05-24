package br.com.alexgirao.tarefa.exception;

/**
 * 
 * @author Alex Girao
 */
public class EmailExistenteException extends RuntimeException {

    private static final long serialVersionUID = -3088530387721373131L;

	public EmailExistenteException(String message) {
        super(message);
    }
    
}