package br.com.alexgirao.tarefa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author Alex Girao
 */
@SpringBootApplication
public class TarefaApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
        SpringApplication.run(TarefaApplication.class, args);
    }
    
}
