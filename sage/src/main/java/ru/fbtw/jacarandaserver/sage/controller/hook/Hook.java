package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;

import java.util.Set;

public interface Hook {
	void hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder);

	Set<HttpMethod> getSupportedMethods();
}
