package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.view.DataModelWithView;
import ru.fbtw.jacarandaserver.sage.view.Model;
import ru.fbtw.jacarandaserver.sage.view.ViewPresenter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MvcHook implements Hook {
	private final HttpMethod method;
	private final RequestProvider[] requestProviders;
	private final Function<Object[], Object> callback;
	private final ViewPresenter<DataModelWithView> presenter;

	public MvcHook(
			HttpMethod method,
			RequestProvider[] requestProviders,
			Function<Object[], Object> callback,
			ViewPresenter<DataModelWithView> presenter
	) {
		this.method = method;
		this.requestProviders = requestProviders;
		this.callback = callback;
		this.presenter = presenter;
	}

	@Override
	public void hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		Model dataModel = new Model();
		Object[] signature = Arrays.stream(requestProviders)
				.map(p -> p.provide(request, responseBuilder, dataModel))
				.toArray();

		String hookResponse = callback.apply(signature).toString();
		DataModelWithView dataModelWithView = new DataModelWithView(hookResponse, dataModel);
		byte[] serializedBody = presenter.present(dataModelWithView);
		responseBuilder.setBody(serializedBody);
	}

	@Override
	public boolean hasMethodSupport(HttpMethod method) {
		return this.method.equals(method);
	}
}
