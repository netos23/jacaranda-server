package ru.fbtw.jacarandaserver.util.exceptions;

public class MissingFactory extends RuntimeException{

	public MissingFactory(String message) {
		super(message);
	}

	public MissingFactory(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingFactory(Throwable cause) {
		super(cause);
	}
}
