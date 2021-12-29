package ru.fbtw.jacarandaserver.sage.controller.exception;

public class MissingContentTypeException extends RuntimeException {
	public MissingContentTypeException() {
	}

	public MissingContentTypeException(String message) {
		super(message);
	}

	public MissingContentTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingContentTypeException(Throwable cause) {
		super(cause);
	}
}
