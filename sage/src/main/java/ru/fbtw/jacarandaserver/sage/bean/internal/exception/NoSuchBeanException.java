package ru.fbtw.jacarandaserver.sage.bean.internal.exception;

public class NoSuchBeanException extends Exception {
	private static final String FORMATTED_MESSAGE = "There is no bean for class or annotation %s";

	public NoSuchBeanException(Class<?> clazz) {
		super(String.format(FORMATTED_MESSAGE, clazz));
	}
}
