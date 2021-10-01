package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.Url;
import ru.fbtw.jacarandaserver.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.util.Map;

public interface RequestHandler {

    default void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException {
        if (!request.getHeaders()
                .containsKey(HttpHeader.HOST.getHeaderName())) {
            throw new BadRequestException("Missing host header. HTTP/1.1 condition violated");
        }
    }

    default void setServerConfiguration(
            ServerContext context,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) {
        responseBuilder.setHttpVersion(context.getHttpVersion());
        responseBuilder.addHeader(HttpHeader.SERVER.getHeaderName(), context.getServerName());


        String connectionPolicy = request != null
                ? request.getHeader(HttpHeader.CONNECTION.getHeaderName())
                : null;
        if (connectionPolicy != null && connectionPolicy.equals("keep-alive")) {
            responseBuilder.addHeader(HttpHeader.CONNECTION.getHeaderName(), "keep-alive");
        } else {
            responseBuilder.addHeader(HttpHeader.CONNECTION.getHeaderName(), "close");
        }
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
