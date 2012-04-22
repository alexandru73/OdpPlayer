package com.school.exceptions;

public class IncorectFileException extends PresentationException {
	private static final long serialVersionUID = 1L;

	public IncorectFileException() {
		super();
	}

	public IncorectFileException(Exception e) {
		super(e);
	}

	public IncorectFileException(String message) {
		super(message);
	}

}
