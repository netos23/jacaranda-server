package ru.fbtw.jacarandaserver.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpResponseBuildException;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class RequestDispatcher {
	private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

	private final Map<HttpMethod, RequestHandler> handlers;
	private final ExceptionRequestHandler exceptionRequestHandler;
	private final ServerConfiguration context;

	public RequestDispatcher(ServerConfiguration context) {
		this.context = context;
		FileHandler fileHandler = new FileHandler(context);

		handlers = new HashMap<>();

		RequestHandler getHandler = new ResourceHandler(fileHandler);
		RequestHandler postHandler = new PostRequestHandler(fileHandler);
		RequestHandler unsupportedHandler = new UnsupportedRequestHandler(fileHandler);
		exceptionRequestHandler = new ExceptionRequestHandler(fileHandler, context);

		handlers.put(HttpMethod.GET, getHandler);
		handlers.put(HttpMethod.POST, postHandler);
		handlers.put(HttpMethod.UNSUPPORTED, unsupportedHandler);

	}

	public void dispatch(
			HttpRequest request,
			HttpResponse.HttpResponseBuilder responseBuilder
	) throws IOException {
		try {


			logger.debug("Handle request");
			 handlers.get(request.getMethod())
					.handle(context, request, responseBuilder);

		} catch (Exception ex) {
			logger.warn("Exception during handle request");
			ex.printStackTrace();
			try {
				exceptionRequestHandler.handle(null, ex);
			} catch (HttpResponseBuildException e) {
				throw new IOException("Error while writing error response occurred");
			}
		}
		logger.debug("Writing response");
	}
}
