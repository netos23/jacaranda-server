package ru.fbtw.jacarandaserver.sage.controller.filter;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.api.serverlet.Filter;

import java.util.List;

public abstract class GenericFilterChain<F extends Filter> implements Filter {
	private final Filter filterChain;

	public GenericFilterChain(List<F> filters) {
		this.filterChain = filters.stream()
				.map(filter -> (Filter) filter)
				.reduce(Filter::compose)
				.orElse(Filter.empty());
	}

	@Override
	public void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
			throws BadRequestException {
		filterChain.doFilter(request, responseBuilder);
	}
}
