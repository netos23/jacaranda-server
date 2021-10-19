package ru.fbtw.jacarandaserver.core.handlers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.api.serverlet.AbstractRequestHandler;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

public class UnsupportedRequestHandler extends AbstractRequestHandler {
    public UnsupportedRequestHandler(ContentTypeResolver contentTypeResolver, FileHandler fileHandler) {
        super(contentTypeResolver, fileHandler);
    }

    @Override
    public void validateRequest(ServerConfiguration context, HttpRequest request) throws BadRequestException {
        throw new BadRequestException("Request type is wrong");
    }
}
