package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.exceptions.HttpRequestBuildException;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final RequestDispatcher requestDispatcher;

    public ConnectionHandler(Socket clientSocket, ServerContext context) {
        this.clientSocket = clientSocket;
        requestDispatcher = new RequestDispatcher(context);
    }

    @Override
    public void run() {
        try {
            requestDispatcher.dispatch(clientSocket);
            //clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
