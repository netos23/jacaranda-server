package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.view.AbstractRestPresenter;


import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public class RestHook implements Hook{
	private final Set<HttpMethod> method;
	private final RequestProvider[] requestProviders;
	private final Function<Object[], Object> callback;
	private final AbstractRestPresenter<Object> presenter;

	public RestHook(
			HttpMethod method,
			RequestProvider[] requestProviders,
			Function<Object[], Object> callback,
			AbstractRestPresenter<Object> presenter
	) {
		this.method = Collections.singleton(method);
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
		responseBuilder.addHeader(HttpHeader.CONTENT_TYPE.getHeaderName(), ContentType.JSON.getValue());
	}

	@Override
	public Set<HttpMethod> getSupportedMethods() {
		return method;
	}

}
