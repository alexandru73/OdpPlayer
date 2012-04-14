package com.school.exceptions;

public class SvgConversionFailedException extends CommandFailedToExecuteExeption {
	private static final long serialVersionUID = 1L;

	public SvgConversionFailedException() {
		super();
	}

	public SvgConversionFailedException(Exception e) {
		super(e);
	}

	public SvgConversionFailedException(String message) {
		super(message);
	}
}
