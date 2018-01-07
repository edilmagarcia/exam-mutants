package com.mercadolibre.exam.mutants.detector.exceptions;

public class DatabaseException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public  DatabaseException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
