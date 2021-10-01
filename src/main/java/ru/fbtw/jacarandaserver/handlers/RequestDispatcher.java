package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher {
    private final Map<HttpMethod, RequestHandler> handlers;
    private final ExceptionRequestHandler exceptionRequestHandler;
    private final ServerContext context;

    public RequestDispatcher(ServerContext context) {
        this.context = context;
        FileHandler fileHandler = new FileHandler(context);

        handlers = new HashMap<>();

        RequestHandler getHandler = new GetRequestHandler(fileHandler);
        RequestHandler postHandler = new PostRequestHandler();
        RequestHandler unsupportedHandler = new UnsupportedRequestHandler();
        exceptionRequestHandler = new ExceptionRequestHandler(fileHandler, context);

        handlers.put(HttpMethod.GET, getHandler);
        handlers.put(HttpMethod.POST, postHandler);
        handlers.put(HttpMethod.UNSUPPORTED, unsupportedHandler);

    }

    public void dispatch(Socket clientSocket) throws IOException {
        HttpResponse httpResponse;
        try {
            HttpRequest httpRequest = HttpRequest.parse(clientSocket.getInputStream(), context);
            httpResponse = handlers.get(httpRequest.getMethod())
                    .handle(context, httpRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
            httpResponse = exceptionRequestHandler.handle(null, ex);
        }
        httpResponse.write(clientSocket.getOutputStream());

        // apply connection header
        if (httpResponse.getHeaders()
                .getOrDefault(HttpHeader.CONNECTION.getName(), "Close").equals("Close")) {
            clientSocket.close();
        }
    }
}
