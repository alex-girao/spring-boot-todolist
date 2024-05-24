package br.com.alexgirao.tarefa.controller.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Alex Girao
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
public class Response<T> {

	private T data;
	private String status;
	private List<String> message;

	public Response(ResponseStatusEnum status, T data) {
		this.status = status.getDescription();
		this.data = data;
	}
	
	public Response(ResponseStatusEnum status, T data, List<String> message) {
		this.status = status.getDescription();
		this.data = data;
		this.message = message;
	}
	
	public Response(ResponseStatusEnum status, List<String> message) {
		this.status = status.getDescription();
		this.message = message;
	}

	public void setStatus(ResponseStatusEnum status) {
		this.status = status.getDescription();
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}

