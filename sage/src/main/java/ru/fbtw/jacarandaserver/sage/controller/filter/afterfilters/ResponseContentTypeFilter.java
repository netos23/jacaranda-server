package ru.fbtw.jacarandaserver.sage.controller.filter.afterfilters;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.controller.exception.MissingContentTypeException;

@Component
public class ResponseContentTypeFilter implements AfterRequestFilter {
	@Override
	public void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
			throws BadRequestException {
		String header = responseBuilder.getHeader(HttpHeader.CONTENT_TYPE.getHeaderName());
		if (header == null) {
			throw new MissingContentTypeException("Response content type missing");
		}
	}
}
