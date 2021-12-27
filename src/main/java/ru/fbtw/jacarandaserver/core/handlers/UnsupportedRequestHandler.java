package ru.fbtw.jacarandaserver.core.handlers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

@Deprecated
public class UnsupportedRequestHandler extends AbstractRequestHandler {
	public UnsupportedRequestHandler(FileHandler fileHandler) {
		super(fileHandler);
	}

	@Override
	public void validateRequest(ServerConfiguration context, HttpRequest request) throws BadRequestException {
		throw new BadRequestException("Request type is wrong");
	}
}
