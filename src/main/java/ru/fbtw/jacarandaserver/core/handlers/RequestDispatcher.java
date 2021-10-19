package ru.fbtw.jacarandaserver.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.serverlet.RequestHandler;
import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpResponseBuildException;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    private final Map<HttpMethod, RequestHandler> handlers;
    private final ExceptionRequestHandler exceptionRequestHandler;
    private final ServerConfiguration context;

    public RequestDispatcher(ServerConfiguration context) {
        this.context = context;
        FileHandler fileHandler = new FileHandler(context);

        handlers = new HashMap<>();

        RequestHandler getHandler = new GetRequestHandler(fileHandler);
        RequestHandler postHandler = new PostRequestHandler(fileHandler);
        RequestHandler unsupportedHandler = new UnsupportedRequestHandler(fileHandler);
        exceptionRequestHandler = new ExceptionRequestHandler(fileHandler, context);

        handlers.put(HttpMethod.GET, getHandler);
        handlers.put(HttpMethod.POST, postHandler);
        handlers.put(HttpMethod.UNSUPPORTED, unsupportedHandler);

    }

    public boolean dispatch(Socket clientSocket) throws IOException {
        HttpResponse httpResponse = null;
        try {
            logger.debug("Parse request");
            HttpRequest httpRequest = HttpRequest.parse(clientSocket.getInputStream(), context);

            logger.debug("Handle request");
            httpResponse = handlers.get(httpRequest.getMethod())
                    .handle(context, httpRequest);

        } catch (Exception ex) {
            logger.warn("Exception during handle request");
            ex.printStackTrace();
            try {
                httpResponse = exceptionRequestHandler.handle(null, ex);
            } catch (HttpResponseBuildException e) {
                throw new IOException("Error while writing error response occurred");
            }
        }
        logger.debug("Writing response");
        httpResponse.write(clientSocket.getOutputStream());

        // apply connection header
        if (httpResponse.getHeaders()
                .getOrDefault(HttpHeader.CONNECTION.getHeaderName(), "Close").equals("Close")) {
            logger.info("Close connection ip: {}", clientSocket.getInetAddress());
            clientSocket.close();
            return false;
        }

        return true;
    }
}
