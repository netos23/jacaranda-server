package ru.fbtw.jacarandaserver.api.requests.exceptions;

public class ResourceNotFoundException extends BadRequestException {

	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
