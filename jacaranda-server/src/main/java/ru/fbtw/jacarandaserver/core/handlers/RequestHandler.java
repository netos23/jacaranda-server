package ru.fbtw.jacarandaserver.core.handlers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.Url;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

import java.util.Map;

@Deprecated
public interface RequestHandler {

	void validateRequest(ServerConfiguration context, HttpRequest request) throws BadRequestException;

	void setServerConfiguration(
			ServerConfiguration context,
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException;

	void handleUrl(
			Url url,
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException;

	void handleQueryParams(
			Map<String, String> queryParams,
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException;

	void handleHeaders(
			Map<String, String> headers,
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException;

	void handleBody(
			String body,
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException;

	void setStatus(
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException;

	default void handle(
			ServerConfiguration context,
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws BadRequestException {
		validateRequest(context, request);

		setServerConfiguration(context, request, responseBuilder);
		handleUrl(request.getUrl(), request, responseBuilder);
		//handleQueryParams(request.getUrl().getQueryParams(), request, responseBuilder);
		handleHeaders(request.getHeaders(), request, responseBuilder);
		handleBody(request.getBody(), request, responseBuilder);

		setStatus(request, responseBuilder);
	}
}
