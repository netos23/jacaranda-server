package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.view.AbstractRestPresenter;

import java.util.Arrays;
import java.util.function.Function;

public class RestHook implements Hook{
	private final HttpMethod method;
	private final RequestProvider[] requestProviders;
	private final Function<Object[], Object> callback;
	private final AbstractRestPresenter<Object> presenter;

	public RestHook(
			HttpMethod method,
			RequestProvider[] requestProviders,
			Function<Object[], Object> callback,
			AbstractRestPresenter<Object> presenter
	) {
		this.method = method;
		this.requestProviders = requestProviders;
		this.callback = callback;
		this.presenter = presenter;
	}


	@Override
	public void hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		Object[] signature = Arrays.stream(requestProviders)
				.map(p -> p.provide(request, responseBuilder))
				.toArray();

		Object hookResponse = callback.apply(signature);
		byte[] serializedBody = presenter.present(hookResponse);
		responseBuilder.setBody(serializedBody);
	}



	@Override
	public boolean hasMethodSupport(HttpMethod method) {
		return this.method.equals(method);
	}
}
