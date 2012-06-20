package com.school.presentation.exceptions;

public class CommandFailedToExecuteExeption extends PresentationException {

	private static final long serialVersionUID = 1L;
	
	public CommandFailedToExecuteExeption() {
		super();
	}
	
	public CommandFailedToExecuteExeption(Exception e) {
		super(e);
	}
	
	public CommandFailedToExecuteExeption(String message) {
		super(message);
	}
}
