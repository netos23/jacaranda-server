package ru.fbtw.jacarndaserver.server;

import ru.fbtw.jacarndaserver.handlers.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer {

    private final ServerSocket mainSocket;
    private final Executor executor;


    public HttpServer(int socket, int maxConnections) throws IOException {
        mainSocket = new ServerSocket(socket);
        executor = Executors.newFixedThreadPool(maxConnections);
    }

    public HttpServer(int maxConnections) throws IOException {
        this(0, maxConnections);
    }

    public void listen() throws IOException {
        while (true) {
            Socket clientSocket = mainSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(clientSocket);
            executor.execute(handler);
        }
    }

}
