package ru.fbtw.configuration.exceptions;

public class ConfigurationBuildException extends Exception {

	public ConfigurationBuildException() {
		super();
	}

	public ConfigurationBuildException(String message) {
		super(message);
	}

	public ConfigurationBuildException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationBuildException(Throwable cause) {
		super(cause);
	}
}
