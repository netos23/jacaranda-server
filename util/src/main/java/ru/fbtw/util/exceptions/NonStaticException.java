package ru.fbtw.util.exceptions;

public class NonStaticException extends RuntimeException {

	public NonStaticException() {
		super();
	}

	public NonStaticException(String message) {
		super(message);
	}

	public NonStaticException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonStaticException(Throwable cause) {
		super(cause);
	}
}
