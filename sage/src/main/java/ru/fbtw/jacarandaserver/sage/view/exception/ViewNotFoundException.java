package ru.fbtw.jacarandaserver.sage.view.exception;

public class ViewNotFoundException extends RuntimeException {

	public ViewNotFoundException() {
	}

	public ViewNotFoundException(String message) {
		super(message);
	}

	public ViewNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewNotFoundException(Throwable cause) {
		super(cause);
	}

	public ViewNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
