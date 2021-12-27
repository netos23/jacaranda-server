package ru.fbtw.configuration.exceptions;

public class MissingConfiguration extends RuntimeException {
	public MissingConfiguration() {
		super();
	}

	public MissingConfiguration(String message) {
		super(message);
	}

	public MissingConfiguration(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingConfiguration(Throwable cause) {
		super(cause);
	}
}
