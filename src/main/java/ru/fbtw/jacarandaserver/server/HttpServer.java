package ru.fbtw.jacarandaserver.server;

import ru.fbtw.jacarandaserver.handlers.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer {

    private final ServerSocket mainSocket;
    private final Executor executor;
    private final ServerContext context;


    public HttpServer(ServerContext context) throws IOException {
        this.context = context;
        mainSocket = new ServerSocket(context.getSocket());
        executor = Executors.newFixedThreadPool(context.getMaxConnections());
    }

    public void listen() throws IOException {
        while (true) {
            Socket clientSocket = mainSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(clientSocket, context);
            handler.run();
        }
    }

}
