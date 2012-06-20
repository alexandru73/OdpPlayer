package com.school.presentation.exceptions;

public class CommandRollbackFailedException extends PresentationException {

	private static final long serialVersionUID = 1L;

	public CommandRollbackFailedException() {
		super();
	}

	public CommandRollbackFailedException(Exception e) {
		super(e);
	}

	public CommandRollbackFailedException(String message) {
		super(message);
	}
}
