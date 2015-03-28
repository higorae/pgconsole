package com.github.luksrn.postgresql.web

import org.springframework.validation.Errors

class InvalidRequestException extends RuntimeException {
	
	Errors errors;

	public InvalidRequestException(String message, Errors errors) {
		super(message);
		this.errors = errors;
	}
}
