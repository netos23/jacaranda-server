package ru.fbtw.jacarandaserver.core.context.filters;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.api.serverlet.Filter;

public class HostFilter implements Filter {
	@Override
	public void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
			throws BadRequestException {
		if (!request.getHeaders()
				.containsKey(HttpHeader.HOST.getHeaderName())) {
			throw new BadRequestException("Missing host header. HTTP/1.1 condition violated");
		}
	}
}
