package ru.fbtw.jacarandaserver.api.serverlet;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.Url;
import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.core.handlers.FileHandler;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractRequestHandler implements RequestHandler {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected final FileHandler fileHandler;

    public AbstractRequestHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void validateRequest(ServerConfiguration context, HttpRequest request) throws BadRequestException {
        if (!request.getHeaders()
                .containsKey(HttpHeader.HOST.getHeaderName())) {
            throw new BadRequestException("Missing host header. HTTP/1.1 condition violated");
        }
    }

    @Override
    public void setServerConfiguration(
            ServerConfiguration context,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) {
        // Set server configuration to response
        responseBuilder.setHttpVersion(context.getHttpVersion());
        responseBuilder.addHeader(HttpHeader.SERVER.getHeaderName(), context.getServerName());

        String httpDate = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss zzz", Locale.ENGLISH)
                .format(new Date());
        responseBuilder.addHeader(HttpHeader.DATE.getHeaderName(), httpDate);

        // Calculate connection policy
        String connectionPolicy = request != null
                ? request.getHeader(HttpHeader.CONNECTION.getHeaderName())
                : null;
        if (connectionPolicy != null) {
            responseBuilder.addHeader(HttpHeader.CONNECTION.getHeaderName(), connectionPolicy);
        } else {
            responseBuilder.addHeader(HttpHeader.CONNECTION.getHeaderName(), "keep-alive");
        }
    }

    @Override
    public void handleUrl(
            Url url,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException {
    }

    @Override
    public void handleQueryParams(
            Map<String, String> queryParams,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException {
    }

    @Override
    public void handleHeaders(
            Map<String, String> headers,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) throws BadRequestException {
        String contentType = ContentType.resolve(request.getUrl().getContextPath());
        String contentTypeHeaderVal = String.format("%s; %s", contentType, DEFAULT_CHARSET.name());
        responseBuilder.addHeader(HttpHeader.CONTENT_TYPE.getHeaderName(), contentTypeHeaderVal);
    }

    @Override
    public void handleBody(
            String body,
            HttpRequest request,
            HttpResponse.HttpResponseBuilder responseBuilder
    ) {
    }

    @Override
    public void setStatus(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
        responseBuilder.setStatus(HttpStatus.OK);
    }
}
