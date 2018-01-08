package com.mercadolibre.exam.mutants.exception;

public class InputValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InputValidationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
