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


    public HttpServer(ServerConfiguration context) throws IOException {
        this.context = context;
        logger.info("Initialize jacaranda server with context path: {}; " +
                "server address: {}; http version: {}", context.getPath(), context.getHost(), context.getHttpVersion());

        mainSocket = new ServerSocket(context.getPort());
        executor = Executors.newFixedThreadPool(context.getMaxConnections());
        logger.info("Listen up to {} connections", context.getMaxConnections());
    }

    public void start() throws IOException {
        logger.info("Server listening port {}", context.getPort());
        while (true) {
            Socket clientSocket = mainSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(clientSocket, context);
            executor.execute(handler);
        }
    }

}
