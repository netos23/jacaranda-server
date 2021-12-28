package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;

public interface Hook<T> {
	T hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder);

	String getHookMapping();

	boolean hasMethodSupport(HttpMethod method);
}
