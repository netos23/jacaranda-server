package ru.fbtw.jacarndaserver.handlers;

import ru.fbtw.jacarndaserver.requests.HttpRequest;
import ru.fbtw.jacarndaserver.requests.HttpResponse;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        HttpRequest request = parseRequest(clientSocket);
        HttpResponse response = handle(request);
        writeResponse(response);

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResponse(HttpResponse response) {

    }

    private HttpResponse handle(HttpRequest request) {
        return null;
    }

    private HttpRequest parseRequest(Socket clientSocket) {
        return null;
    }
}
