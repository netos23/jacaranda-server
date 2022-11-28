package ru.fbtw.jacarandaserver.sage.bean.internal.exception;

public class BeanCreationException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Can`t create bean for class %s";

	public BeanCreationException(Class<?> declaringClass, Throwable cause) {
		super(String.format(MESSAGE_FORMAT, declaringClass), cause);
	}
}
