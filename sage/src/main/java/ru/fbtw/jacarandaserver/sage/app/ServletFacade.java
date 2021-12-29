package ru.fbtw.jacarandaserver.sage.app;

import ru.fbtw.jacarandaserver.api.annotations.WebApplication;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;

@WebApplication
public class ServletFacade implements Servlet {
	private final SageApplication sageApplication;

	public ServletFacade() {
		sageApplication = SageApplicationLoader.createSageApp();
	}

	@Override
	public void service(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		sageApplication.handle(request, responseBuilder);
	}
}
