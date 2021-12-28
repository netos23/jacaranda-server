package ru.fbtw.jacarandaserver.sage.controller.request.providers;

import ru.fbtw.jacarandaserver.sage.view.Model;

public class RequestProviders {
	private RequestProviders() {

	}

	public static RequestProvider getRequestParamProvider(String param, Class<?> type) {
		return (request, responseBuilder) -> {
			String rawParam = request.getUrl()
					.getQueryParams()
					.get(param);
			// TODO: фабрика в объект
			return null;
		};
	}

	public static RequestProvider getRequestProvider() {
		return (request, responseBuilder) -> request;
	}

	public static RequestProvider getResponseBuilderProvider() {
		return (request, responseBuilder) -> responseBuilder;
	}

	public static RequestProvider getAllRequestParamsProvider() {
		return (request, responseBuilder) -> request.getUrl().getQueryParams();
	}

	public static RequestProvider getRequestBodyProvider(Class<?> type) {
		return (request, responseBuilder) -> {
			// TODO: фабрика в объект
			return null;
		};
	}

	public static RequestProvider getModelProvider(Model model) {
		return (request, responseBuilder) -> model;
	}
}
