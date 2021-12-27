package ru.fbtw.jacarandaserver.api.serverlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;

public interface Filter {
	void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) throws BadRequestException;

	default Filter andThen(Filter next) {
		return (request, responseBuilder) -> {
			doFilter(request, responseBuilder);
			next.doFilter(request, responseBuilder);
		};
	}
}
