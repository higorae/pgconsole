package com.github.luksrn.postgresql.utils

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
class ErrorResource {
	String code;
	String message;
	List<FieldErrorResource> fieldErrors;
	
	public ErrorResource(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}	
	
}
