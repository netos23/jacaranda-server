package ru.fbtw.jacarandaserver.api.serverlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;

public interface Servlet {
	void service(final HttpRequest request, final HttpResponse.HttpResponseBuilder responseBuilder);
}
