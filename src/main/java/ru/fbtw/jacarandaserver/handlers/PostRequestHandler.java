package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.requests.exceptions.NotImplementedException;
import ru.fbtw.jacarandaserver.server.ServerContext;

public class PostRequestHandler extends AbstractRequestHandler {
    public PostRequestHandler(ContentTypeResolver contentTypeResolver, FileHandler fileHandler) {
        super(contentTypeResolver, fileHandler);
    }

    @Override
    public void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException {
        throw new NotImplementedException("Post requests has not implemented yet");
    }
}
