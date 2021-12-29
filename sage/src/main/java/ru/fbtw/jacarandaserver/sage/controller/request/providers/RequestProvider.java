package ru.fbtw.jacarandaserver.sage.controller.request.providers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;

@FunctionalInterface
public interface RequestProvider {
	Object provide(
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder,
			Object... flags
	);
}
