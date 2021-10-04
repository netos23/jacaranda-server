package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.Url;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.util.Map;

public interface RequestHandler {

    void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException;

    void setServerConfiguration(
            ServerContext context,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException;

    void handleUrl(
            Url url,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException;

    void handleQueryParams(
            Map<String, String> queryParams,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException;

    void handleHeaders(
            Map<String, String> headers,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException;

    void handleBody(
            String body,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException;

    void setStatus(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder);

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
