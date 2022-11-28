package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMappingHandler;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProviders;
import ru.fbtw.jacarandaserver.sage.view.JsonRestPresenter;

import java.lang.reflect.Method;

public class RestHookFactory extends AbstractHookFactory {
	private final JsonRestPresenter presenter;

	public RestHookFactory(
			RequestProviders providers,
			JsonRestPresenter presenter,
			RequestMappingHandler mappingHandler
	) {
		super(providers, mappingHandler);
		this.presenter = presenter;
	}

	@Override
	public Hook createHook(Object controller, Method hookCandidate, HttpMethod method) {
		return new RestHook(
				method,
				getRequestProviders(hookCandidate),
				methodToCallback(hookCandidate, controller),
				presenter
		);
	}
}
