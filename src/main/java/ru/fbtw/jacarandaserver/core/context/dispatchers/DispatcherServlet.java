package ru.fbtw.jacarandaserver.core.context.dispatchers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.core.server.HttpServer;

import java.util.List;
import java.util.Optional;


public class DispatcherServlet implements Servlet {
	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

	private final Servlet defaultServlet;
	private final ServletMappingHandler mappingHandler;

	public DispatcherServlet(Servlet defaultServlet, ServletMappingHandler mappingHandler) {
		this.defaultServlet = defaultServlet;
		this.mappingHandler = mappingHandler;
	}

	@Override
	public void service(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		Optional<Servlet> optionalServlet = mappingHandler.getServlet(request.getUrl().getContextPath());
		Servlet servlet = optionalServlet.orElse(defaultServlet);
		servlet.service(request, responseBuilder);
	}

	@Override
	public String getRootPath() {
		throw new UnsupportedOperationException();
	}

	public Servlet getDefaultServlet() {
		return defaultServlet;
	}

	public ServletMappingHandler getMappingHandler() {
		return mappingHandler;
	}
}
