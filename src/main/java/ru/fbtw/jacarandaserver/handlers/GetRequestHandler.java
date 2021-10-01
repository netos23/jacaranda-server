package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.Url;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.requests.exceptions.ResurseNotFoundException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.File;

public class GetRequestHandler implements RequestHandler {

    private final FileHandler fileHandler;

    public GetRequestHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException {
        RequestHandler.super.validateRequest(context, request);
        if (request.getBody() != null) {
            throw new BadRequestException("Body is not supported in GET request context");
        }
    }

    @Override
    public void handleUrl(Url url, HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
            throws ResurseNotFoundException {
        // todo: refactor
        File file = fileHandler.getFile(url.getContextPath());
        String contentType = fileHandler.getContentType(file);
        byte[] responseBody = fileHandler.handle(file);

        responseBuilder.addHeader(HttpHeader.CONTENT_TYPE.getHeaderName(), contentType);
        responseBuilder.addHeader(HttpHeader.CONTENT_LENGTH.getHeaderName(), Integer.toString(responseBody.length));
        responseBuilder.setBody(responseBody);
    }
}
