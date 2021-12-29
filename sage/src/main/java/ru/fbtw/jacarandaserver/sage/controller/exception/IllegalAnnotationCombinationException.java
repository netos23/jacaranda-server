package ru.fbtw.jacarandaserver.sage.controller.exception;

public class IllegalAnnotationCombinationException extends RuntimeException {

	public static final String FORMATTED_MESSAGE = "Two or more possible ways for interpret controller param %s. ";

	public IllegalAnnotationCombinationException(String info) {
		super(String.format(FORMATTED_MESSAGE, info));
	}

	public IllegalAnnotationCombinationException() {
		this("");
	}
}
