package ru.fbtw.jacarandaserver.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpRequestBuildException;
import ru.fbtw.jacarandaserver.core.context.ServletContext;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

	private final Socket clientSocket;
	private final ServletContext context;

	public ConnectionHandler(Socket clientSocket, ServletContext context) {
		this.clientSocket = clientSocket;
		this.context = context;
	}

	@Override
	public void run() {
		logger.info("Handle new connection: {}", clientSocket.getInetAddress());
		boolean isOpened = true;
		do {
			try {
				logger.debug("Waiting for request");
				HttpRequest request;
				try {
					ServerConfiguration configuration = context.getConfiguration();

					request = HttpRequest.parse(
							clientSocket.getInputStream(),
							configuration.getHost(),
							configuration.getProtocol()
					);

					logger.debug("Handle request");
				} catch (IOException e) {
					logger.error("IO error occurred during request parse");
					e.printStackTrace();
					shutdownConnection();
					return;
				}
				HttpResponse.HttpResponseBuilder responseBuilder = HttpResponse.newBuilder();


				context.getPreRequestFilterChain().doFilter(request, responseBuilder);
				context.getDispatcherServlet().service(request, responseBuilder);
				context.getAfterRequestFilterChain().doFilter(request, responseBuilder);
				isOpened = context.getKeepAliveResolver().resolve(request, responseBuilder);

				HttpResponse response = responseBuilder.build();
				try {
					response.write(clientSocket.getOutputStream());
					logger.debug("Writing request");
				} catch (IOException e) {
					logger.error("IO error occurred during request write");
					e.printStackTrace();
					shutdownConnection();
					return;
				}

			} catch (HttpRequestBuildException | BadRequestException e) {
				e.printStackTrace();
				resolveException(e);
			}
		} while (isOpened);

		shutdownConnection();
	}

	private void resolveException(Exception e) {
		HttpResponse response;
		response = context.getExceptionHandler().handle(e);
		try {
			response.write(clientSocket.getOutputStream());
			logger.info("Resolved: exception[{}] ", e.toString());
		} catch (IOException ex) {
			logger.error("IO error occurred");
			ex.printStackTrace();
			shutdownConnection();
		}
	}

	private void shutdownConnection() {
		logger.info("Shutdown connection");
		try {
			clientSocket.close();
		} catch (IOException ex) {
			logger.error("Error during connection close");
			ex.printStackTrace();
		}
	}

}
