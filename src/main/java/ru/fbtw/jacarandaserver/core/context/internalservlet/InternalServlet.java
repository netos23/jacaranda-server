package ru.fbtw.jacarandaserver.core.context.internalservlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.sage.app.SageApplication;
import ru.fbtw.jacarandaserver.sage.app.SageApplicationLoader;

public class InternalServlet implements Servlet {
	private final SageApplication sageApplication;

	public InternalServlet() {
		sageApplication = SageApplicationLoader.createSageApp();
	}

	@Override
	public void service(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		sageApplication.handle(request, responseBuilder);
	}

	@Override
	public String getRootPath() {
		return "/";
	}
}
