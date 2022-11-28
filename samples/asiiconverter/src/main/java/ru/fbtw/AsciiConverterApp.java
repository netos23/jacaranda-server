package ru.fbtw;

import ru.fbtw.jacarandaserver.api.annotations.WebApplication;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.sage.app.SageApplication;
import ru.fbtw.jacarandaserver.sage.app.SageApplicationLoader;

@WebApplication
public class AsciiConverterApp implements Servlet {
	private final SageApplication sageApplication;

	public AsciiConverterApp() {
		sageApplication = SageApplicationLoader.createSageApp();
	}

	@Override
	public void service(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		sageApplication.handle(request, responseBuilder);
	}

	@Override
	public String getRootPath() {
		return sageApplication.getMvcConfig().getRootPath();
	}
}
