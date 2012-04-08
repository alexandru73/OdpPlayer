package com.school.exceptions;

public class UploadedDataNotFoundException extends PresentationException {
	private static final long serialVersionUID = 1L;

	public UploadedDataNotFoundException() {
		super();
	}

	public UploadedDataNotFoundException(Exception e) {
		super(e);
	}

	public UploadedDataNotFoundException(String message) {
		super(message);
	}
}
