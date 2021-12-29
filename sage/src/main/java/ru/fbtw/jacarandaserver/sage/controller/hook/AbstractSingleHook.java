package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.view.ViewPresenter;

import java.util.Arrays;
import java.util.function.Function;

@Deprecated
public abstract class AbstractSingleHook<T> implements Hook {
	private final HttpMethod method;
	private final RequestProvider[] requestProviders;
	private final Function<Object[], T> callback;
	private final ViewPresenter<T> presenter;

	protected AbstractSingleHook(
			HttpMethod method,
			RequestProvider[] requestProviders,
			Function<Object[], T> callback,
			ViewPresenter<T> presenter
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

		T hookResponse = callback.apply(signature);
		byte[] serializedBody = presenter.present(hookResponse);
		responseBuilder.setBody(serializedBody);
	}



}
