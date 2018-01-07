package com.mercadolibre.exam.mutants.detector.exceptions;

public class ServiceException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public  ServiceException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
