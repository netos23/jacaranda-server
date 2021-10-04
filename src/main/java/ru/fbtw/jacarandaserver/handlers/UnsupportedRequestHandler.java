package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.server.ServerContext;

public class UnsupportedRequestHandler extends AbstractRequestHandler {
    public UnsupportedRequestHandler(ContentTypeResolver contentTypeResolver, FileHandler fileHandler) {
        super(contentTypeResolver, fileHandler);
    }

    @Override
    public void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException {
        throw new BadRequestException("Request type is wrong");
    }
}
