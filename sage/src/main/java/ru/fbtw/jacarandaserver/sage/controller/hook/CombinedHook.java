package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;

import java.util.Map;

public class CombinedHook implements Hook{
	private final Map<HttpMethod, Hook> httpMethodHookMap;

	public CombinedHook(Map<HttpMethod, Hook> httpMethodHookMap) {
		this.httpMethodHookMap = httpMethodHookMap;
	}

	@Override
	public void hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		HttpMethod method = request.getMethod();
		Hook hook = httpMethodHookMap.get(method);
		hook.hang(request,responseBuilder);
	}

	@Override
	public boolean hasMethodSupport(HttpMethod method) {
		return httpMethodHookMap.containsKey(method);
	}
}
