package com.school.exceptions;

public class CommandNotFoundException extends PresentationException {

	private static final long serialVersionUID = 1L;

	public CommandNotFoundException() {
		super();
	}
	
	public CommandNotFoundException(Exception e) {
		super(e);
	}
	
	public CommandNotFoundException(String message) {
		super(message);
	}

	
}
