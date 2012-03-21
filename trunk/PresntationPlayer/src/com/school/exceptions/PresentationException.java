package com.school.exceptions;

public class PresentationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public PresentationException() {
		super();
	}
	
	public PresentationException(Exception e) {
		super(e);
	}
	
	public PresentationException(String message) {
		super(message);
	}
}
