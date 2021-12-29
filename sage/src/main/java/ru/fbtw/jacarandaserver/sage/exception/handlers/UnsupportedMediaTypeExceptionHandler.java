package ru.fbtw.jacarandaserver.sage.exception.handlers;

import ru.fbtw.jacarandaserver.api.requests.exceptions.UnsupportedContentTypeException;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.exception.ExceptionResponseEntity;
import ru.fbtw.jacarandaserver.sage.exception.GenericExceptionHandlerBean;

@Component
public class UnsupportedMediaTypeExceptionHandler implements GenericExceptionHandlerBean {
	@Override
	public ExceptionResponseEntity handle(Exception ex) {
		return null;
	}

	@Override
	public Class<? extends Exception> getExceptionType() {
		return UnsupportedContentTypeException.class;
	}
}
