package ru.fbtw.jacarandaserver.sage.controller.exception;

public class MissingProviderException extends RuntimeException {

	public static final String FORMATTED_MESSAGE = "Can`t find provider for param%s";

	public MissingProviderException(String cause) {
		super(String.format(FORMATTED_MESSAGE, cause));
	}
}
