package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpRequestBuildException;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final ServerContext context;
    private final ContextDescriptor descriptor;

    public ConnectionHandler(Socket clientSocket, ServerContext context) {
        this.clientSocket = clientSocket;
        this.context = context;
        descriptor = new ContextDescriptor(context.getPath());
    }

    @Override
    public void run() {
        try {
            HttpRequest request = HttpRequest.parse(clientSocket.getInputStream(), context);

            clientSocket.close();
        } catch (HttpRequestBuildException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeResponse(HttpResponse response) {

    }

    private HttpResponse handle(HttpRequest request) {
        return null;
    }
}
