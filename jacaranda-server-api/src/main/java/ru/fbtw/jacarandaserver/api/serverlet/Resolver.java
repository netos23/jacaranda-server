package ru.fbtw.jacarandaserver.api.serverlet;

import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;

public interface Resolver<T> {
	T resolve(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder);
}
