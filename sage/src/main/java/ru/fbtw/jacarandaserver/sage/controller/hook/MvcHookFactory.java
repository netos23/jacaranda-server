package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMappingHandler;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProviders;
import ru.fbtw.jacarandaserver.sage.view.DataModelWithView;
import ru.fbtw.jacarandaserver.sage.view.MvcViewPresenter;
import ru.fbtw.jacarandaserver.sage.view.ViewPresenter;

import java.lang.reflect.Method;

@Component
public class MvcHookFactory extends AbstractHookFactory {
	private final ViewPresenter<DataModelWithView> presenter;

	public MvcHookFactory(
			MvcViewPresenter presenter,
			RequestProviders providers,
			RequestMappingHandler mappingHandler
	) {
		super(providers, mappingHandler);
		this.presenter = presenter;
	}


	@Override
	public Hook createHook(Object controller, Method hookCandidate, HttpMethod method) {
		return new MvcHook(
				method,
				getRequestProviders(hookCandidate),
				methodToCallback(hookCandidate, controller),
				presenter
		);
	}
}
