package ru.fbtw.jacarandaserver.api.serverlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;

public interface Servlet {
	void service(final HttpRequest request, final HttpRequest.HttpRequestBuilder responseBuilder);
}
