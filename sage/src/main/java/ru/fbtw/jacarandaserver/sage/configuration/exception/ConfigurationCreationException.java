package ru.fbtw.jacarandaserver.sage.configuration.exception;

public class ConfigurationCreationException extends RuntimeException {
	public ConfigurationCreationException() {
	}

	public ConfigurationCreationException(String message) {
		super(message);
	}

	public ConfigurationCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationCreationException(Throwable cause) {
		super(cause);
	}

	public ConfigurationCreationException(
			String message,
			Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace
	) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
