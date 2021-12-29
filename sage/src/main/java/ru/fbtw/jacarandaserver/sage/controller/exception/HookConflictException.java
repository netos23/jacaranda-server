package ru.fbtw.jacarandaserver.sage.controller.exception;

public class HookConflictException extends RuntimeException {
	public static final String MESSAGE = "Hooks with same path detected.";

	public HookConflictException() {
		super(MESSAGE);
	}
}
