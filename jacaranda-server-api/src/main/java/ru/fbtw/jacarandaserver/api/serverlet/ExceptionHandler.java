package ru.fbtw.jacarandaserver.api.serverlet;

import ru.fbtw.jacarandaserver.api.requests.HttpResponse;

public interface ExceptionHandler {
	HttpResponse handle(Exception exception);
}
