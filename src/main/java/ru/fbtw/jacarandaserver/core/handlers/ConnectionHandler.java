package ru.fbtw.jacarandaserver.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    private final Socket clientSocket;
    private final RequestDispatcher requestDispatcher;

    public ConnectionHandler(Socket clientSocket, ServerConfiguration configuration) {
        this.clientSocket = clientSocket;
        requestDispatcher = new RequestDispatcher(configuration);
    }

    @Override
    public void run() {
        logger.info("Handle new connection: {}", clientSocket.getInetAddress());
        try {
            boolean isOpened;
            do {
                isOpened = requestDispatcher.dispatch(clientSocket);
            } while (isOpened);
        } catch (IOException e) {
            logger.error("Read-Write exception: client reject connection: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
