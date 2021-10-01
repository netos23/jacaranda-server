package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.server.ServerContext;

public class ExceptionRequestHandler implements RequestHandler {

    private final FileHandler handler;
    private final ServerContext context;

    public ExceptionRequestHandler(FileHandler handler, ServerContext context) {
        this.handler = handler;
        this.context = context;
    }



    public HttpResponse handle(HttpRequest request, Exception ex) {
        HttpResponse.HttpResponseBuilder responseBuilder = HttpResponse.newBuilder();
        RequestHandler.super.setServerConfiguration(context, request, responseBuilder);
        responseBuilder.setStatus(HttpStatus.BAD_REQUEST);

        return responseBuilder.build();
    }
}
