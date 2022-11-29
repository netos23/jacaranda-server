package ru.fbtw.jacarandaserver.sage.controller.filter.afterfilters;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;

@Component
public class StatusFilter implements AfterRequestFilter {
	@Override
	public void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
			throws BadRequestException {
		responseBuilder.setStatus(HttpStatus.OK);
	}
}
