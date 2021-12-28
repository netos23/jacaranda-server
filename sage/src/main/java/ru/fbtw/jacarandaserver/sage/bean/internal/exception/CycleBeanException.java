package ru.fbtw.jacarandaserver.sage.bean.internal.exception;

public class CycleBeanException extends RuntimeException {
	public CycleBeanException() {
		super("Cycle beans detected");
	}
}
