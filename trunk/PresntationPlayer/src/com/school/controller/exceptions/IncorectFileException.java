package com.school.controller.exceptions;

import com.school.presentation.exceptions.PresentationException;

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
