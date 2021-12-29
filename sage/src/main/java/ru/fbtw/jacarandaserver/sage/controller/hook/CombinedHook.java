package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.exception.HookConflictException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CombinedHook implements Hook {
	private final Map<HttpMethod, Hook> httpMethodHookMap;

	public CombinedHook() {
		this.httpMethodHookMap = new HashMap<>();
	}

	public void addHook(Hook hook) {
		long overrideMethods = hook.getSupportedMethods()
				.stream()
				.filter(httpMethodHookMap::containsKey)
				.count();

		if (overrideMethods > 0) {
			throw new HookConflictException();
		}

		hook.getSupportedMethods()
				.forEach(method -> httpMethodHookMap.put(method, hook));
	}

	@Override
	public void hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		HttpMethod method = request.getMethod();
		Hook hook = httpMethodHookMap.get(method);
		hook.hang(request, responseBuilder);
	}

	@Override
	public Set<HttpMethod> getSupportedMethods() {
		return httpMethodHookMap.keySet();
	}


}
