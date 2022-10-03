package ru.fbtw.jacarandaserver.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.core.bootloader.ServletBootloader;
import ru.fbtw.jacarandaserver.core.bootloader.ServletBootstrap;
import ru.fbtw.jacarandaserver.core.context.ServletContext;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

	private final ServerSocket mainSocket;
	private final ExecutorService executor;
	private final ServletContext context;
	private final Thread bootstrap;
	private final ServletBootloader bootloader;


	public HttpServer(ServerConfiguration configuration) throws IOException {
		logger.info("Initialize jacaranda server with configuration path: {}; " +
						"server address: {}; http version: {}",
				configuration.getPath(), configuration.getHost(), configuration.getHttpVersion());

		context = ServletContext.createContext(configuration);
		bootloader = new ServletBootloader(context.getMappingHandler(), Collections.singletonList(""));
		bootstrap = new Thread(new ServletBootstrap(bootloader, ".", 5000));

		mainSocket = new ServerSocket(configuration.getPort());
		executor = Executors.newFixedThreadPool(configuration.getMaxConnections());

	}

	public void start() throws IOException {
		bindShutdownHook();
		bootloader.load(".");
		bootstrap.start();

		logger.info("Listen up to {} connections", context.getConfiguration().getMaxConnections());
		logger.info("Server listening port {}", context.getConfiguration().getPort());

		while (!Thread.currentThread().isInterrupted()) {
			Socket clientSocket = mainSocket.accept();
			ConnectionHandler handler = new ConnectionHandler(clientSocket, context);
			executor.execute(handler);
		}

	}

	private void bindShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Shut down server");
			executor.shutdown();
			bootstrap.interrupt();
		}));
	}

}
