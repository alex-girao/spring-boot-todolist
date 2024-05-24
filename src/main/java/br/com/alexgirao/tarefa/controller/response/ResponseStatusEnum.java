package br.com.alexgirao.tarefa.controller.response;

import lombok.Getter;

/**
 * 
 * @author Alex Girao
 */
@Getter
public enum ResponseStatusEnum {

	SUCCESS("success"), 
	ERROR("error"), 
	INFO("info"), 
	WARNING("warning");

	private String description;

	private ResponseStatusEnum(String description) {
		this.description = description;
	}
}

