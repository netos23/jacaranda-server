package ru.fbtw.jacarandaserver.sage.exception.handlers;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.sage.exception.ExceptionResponseEntity;
import ru.fbtw.jacarandaserver.sage.exception.GenericExceptionHandlerBean;
import ru.fbtw.jacarandaserver.sage.exception.ResourceNotFoundException;

public class NotFoundExceptionHandler implements GenericExceptionHandlerBean {
	@Override
	public ExceptionResponseEntity handle(Exception ex) {
		return new ExceptionResponseEntity(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
		);
	}

	@Override
	public Class<ResourceNotFoundException> getExceptionType() {
		return ResourceNotFoundException.class;
	}
}
