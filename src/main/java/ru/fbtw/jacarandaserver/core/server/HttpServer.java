package ru.fbtw.jacarandaserver.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.core.handlers.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private final ServerSocket mainSocket;
    private final ExecutorService executor;
    private final ServerConfiguration context;


    public HttpServer(ServerConfiguration configuration) throws IOException {
        this.context = configuration;
        logger.info("Initialize jacaranda server with configuration path: {}; " +
                "server address: {}; http version: {}",
                configuration.getPath(), configuration.getHost(), configuration.getHttpVersion());

        mainSocket = new ServerSocket(configuration.getPort());
        executor = Executors.newFixedThreadPool(configuration.getMaxConnections());
        logger.info("Listen up to {} connections", configuration.getMaxConnections());
    }

    public void start() throws IOException {
        logger.info("Server listening port {}", context.getPort());
        while (!Thread.currentThread().isInterrupted()) {
            Socket clientSocket = mainSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(clientSocket, context);
            executor.execute(handler);
        }
    }

}
