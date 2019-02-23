package com.softbank.recipesitory.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Messages{
	GET_ONE("Recipe details by id"),
	CREATION_SUCCESS("Recipe successfully created!"),
	CREATION_FAILED("Recipe creation failed!"),
	NOT_FOUND("No Recipe found");
	
	private String message;
	
	private Messages(String msg) {
		message = msg;
	}
	
	@JsonValue
	public String getMessage() {
		return message;
	}
}
