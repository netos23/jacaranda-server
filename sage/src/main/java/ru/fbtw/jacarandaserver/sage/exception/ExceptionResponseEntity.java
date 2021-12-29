package ru.fbtw.jacarandaserver.sage.exception;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpStatus;

public class ExceptionResponseEntity {
	private final HttpStatus status;
	private final String message;

	public ExceptionResponseEntity(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
