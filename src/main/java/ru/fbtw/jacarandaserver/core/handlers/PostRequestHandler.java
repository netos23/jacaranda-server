package ru.fbtw.jacarandaserver.core.handlers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.api.requests.exceptions.NotImplementedException;
import ru.fbtw.jacarandaserver.api.serverlet.AbstractRequestHandler;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

public class PostRequestHandler extends AbstractRequestHandler {
    public PostRequestHandler(FileHandler fileHandler) {
        super( fileHandler);
    }

    @Override
    public void validateRequest(ServerConfiguration context, HttpRequest request) throws BadRequestException {
        throw new NotImplementedException("Post requests has not implemented yet");
    }
}
