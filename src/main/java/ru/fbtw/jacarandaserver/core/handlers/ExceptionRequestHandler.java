package ru.fbtw.jacarandaserver.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.api.requests.exceptions.*;
import ru.fbtw.jacarandaserver.api.serverlet.AbstractRequestHandler;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;
import ru.fbtw.jacarandaserver.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExceptionRequestHandler extends AbstractRequestHandler {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionRequestHandler.class);

	private final ServerConfiguration context;

	public ExceptionRequestHandler(
			FileHandler fileHandler,
			ServerConfiguration context
	) {
		super(fileHandler);
		this.context = context;
	}

	@Deprecated
	public HttpResponse handle(HttpRequest request, Exception ex) throws HttpResponseBuildException {
		HttpResponse.HttpResponseBuilder responseBuilder = HttpResponse.newBuilder();
		handle(request, responseBuilder, ex);
		return responseBuilder.build();
	}

	public void handle(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder, Exception ex)
			throws HttpResponseBuildException {
		super.setServerConfiguration(context, request, responseBuilder);

		HttpStatus status;
		if (ex instanceof ResourceNotFoundException) {
			status = HttpStatus.NOT_FOUND;
		} else if (ex instanceof NotImplementedException) {
			status = HttpStatus.NOT_IMPLEMENTED;
		} else if (ex instanceof BadRequestException || ex instanceof HttpRequestBuildException) {
			status = HttpStatus.BAD_REQUEST;
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		try {
			String template = IOUtils.readAllStrings(new File(context.getErrorTemplate()));
			String body = String.format(template, status.getCode(), status.name());
			byte[] byteBody = body.getBytes(StandardCharsets.UTF_8);

			responseBuilder.setBody(byteBody);
			responseBuilder.addHeader(HttpHeader.CONTENT_LENGTH.getHeaderName(), Integer.toString(byteBody.length));
		} catch (IOException e) {
			logger.warn("Cant load error template");
			e.printStackTrace();
		}

		responseBuilder.setStatus(status);
	}
}
