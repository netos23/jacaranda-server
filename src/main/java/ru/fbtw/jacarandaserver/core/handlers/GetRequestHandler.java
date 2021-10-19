package ru.fbtw.jacarandaserver.core.handlers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.Url;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.api.requests.exceptions.ResourceNotFoundException;
import ru.fbtw.jacarandaserver.api.serverlet.AbstractRequestHandler;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.io.File;

public class GetRequestHandler extends AbstractRequestHandler {

	public GetRequestHandler(FileHandler fileHandler) {
		super(fileHandler);
	}

	@Override
	public void validateRequest(ServerConfiguration context, HttpRequest request) throws BadRequestException {
		super.validateRequest(context, request);
		if (request.getBody() != null) {
			throw new BadRequestException("Body is not supported in GET request context");
		}
	}

	@Override
	public void handleUrl(Url url, HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
			throws ResourceNotFoundException {
		// todo: refactor
		File file = fileHandler.getFile(url.getContextPath());
		byte[] responseBody = fileHandler.handle(file);

		responseBuilder.addHeader(HttpHeader.CONTENT_LENGTH.getHeaderName(), Integer.toString(responseBody.length));
		responseBuilder.setBody(responseBody);
	}
}
