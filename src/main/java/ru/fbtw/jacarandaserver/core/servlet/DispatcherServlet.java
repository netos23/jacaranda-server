package ru.fbtw.jacarandaserver.core.servlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpResponseBuildException;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.core.handlers.ExceptionRequestHandler;
import ru.fbtw.jacarandaserver.core.handlers.ResourceHandler;


public class DispatcherServlet implements Servlet {

	private ExceptionRequestHandler exceptionRequestHandler;
	private ResourceHandler resourceHandler;
	private ServletMappingHandler mappingHandler;

	public DispatcherServlet() {

	}


	@Override
	public void service(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		try {
			Servlet servlet = mappingHandler.getServlet(request.getUrl().getContextPath());
			servlet.service(request, responseBuilder);
		} catch (Exception ex) {
			try {
				exceptionRequestHandler.handle(request, responseBuilder, ex);
			} catch (HttpResponseBuildException e) {
				e.printStackTrace();
			}
		}
	}
}
