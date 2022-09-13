package ru.fbtw.jacarandaserver.core.context.internalservlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;
import ru.fbtw.jacarandaserver.core.handlers.RequestDispatcher;

import java.io.IOException;

public class InternalServlet implements Servlet {
	private final RequestDispatcher requestDispatcher;

	public InternalServlet(ServerConfiguration configuration) {
		requestDispatcher = new RequestDispatcher(configuration);
	}

	@Override
	public void service(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		try {
			requestDispatcher.dispatch(request, responseBuilder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
