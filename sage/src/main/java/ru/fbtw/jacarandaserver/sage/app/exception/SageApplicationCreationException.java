package ru.fbtw.jacarandaserver.sage.app.exception;

import ru.fbtw.jacarandaserver.sage.bean.internal.exception.NoSuchBeanException;

public class SageApplicationCreationException extends RuntimeException {
	public SageApplicationCreationException() {
	}

	public SageApplicationCreationException(String message) {
		super(message);
	}

	public SageApplicationCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SageApplicationCreationException(Throwable cause) {
		super(cause);
	}

	public SageApplicationCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
