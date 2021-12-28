package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;

import java.util.Arrays;
import java.util.function.Function;

public class SingleHook<T> implements Hook<T> {
	private final String hookMapping;
	private final HttpMethod method;
	private final RequestProvider[] requestProviders;
	private final Function<Object[], T> callback;

	protected SingleHook(
			String hookMapping,
			HttpMethod method,
			RequestProvider[] requestProviders,
			Function<Object[], T> callback
	) {
		this.hookMapping = hookMapping;
		this.method = method;
		this.requestProviders = requestProviders;
		this.callback = callback;
	}


	@Override
	public T hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		Object[] signature = Arrays.stream(requestProviders)
				.map(p -> p.provide(request, responseBuilder))
				.toArray();
		return callback.apply(signature);
	}

	@Override
	public String getHookMapping() {
		return hookMapping;
	}

	@Override
	public boolean hasMethodSupport(HttpMethod method) {
		return this.method.equals(method);
	}
}
