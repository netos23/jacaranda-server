package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.*;
import ru.fbtw.jacarandaserver.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.util.Map;

public interface RequestHandler {

    default void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException {
        if (!request.getHeaders()
                .containsKey(HttpHeader.HOST.getName())) {
            throw new BadRequestException("Missing host header. HTTP/1.1 condition violated");
        }
    }

    default void setServerConfiguration(
            ServerContext context,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) {
        responseBuilder.setHttpVersion(context.getHttpVersion());
        responseBuilder.addHeader(HttpHeader.CONNECTION.name(),"keep-alive");
        responseBuilder.addHeader(HttpHeader.KEEP_ALIVE.name(),"500");
    }

    default void handleUrl(
            Url url,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException {
    }

    default void handleQueryParams(
            Map<String, String> queryParams,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException {
    }

    default void handleHeaders(
            Map<String, String> headers,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException {
    }

    default void handleBody(
            String body,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) {
    }

    default void setStatus(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
        responseBuilder.setStatus(HttpStatus.OK);
    }

    default HttpResponse handle(ServerContext context, HttpRequest request) throws BadRequestException {
        validateRequest(context, request);
        HttpResponse.HttpResponseBuilder responseBuilder = HttpResponse.newBuilder();

        setServerConfiguration(context, request, responseBuilder);
        handleUrl(request.getUrl(), request, responseBuilder);
        handleQueryParams(request.getUrl().getQueryParams(), request, responseBuilder);
        handleHeaders(request.getHeaders(), request, responseBuilder);
        handleBody(request.getBody(), request, responseBuilder);
        setStatus(request, responseBuilder);

        return responseBuilder.build();
    }
}
