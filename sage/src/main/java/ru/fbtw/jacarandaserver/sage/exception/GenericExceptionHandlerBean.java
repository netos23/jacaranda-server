package ru.fbtw.jacarandaserver.sage.exception;

public interface GenericExceptionHandlerBean {
	ExceptionResponseEntity handle(Exception ex);
	Class<? extends Exception> getExceptionType();
}
