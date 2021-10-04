package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.Url;
import ru.fbtw.jacarandaserver.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class AbstractRequestHandler implements RequestHandler {

    protected final ContentTypeResolver contentTypeResolver;
    protected final FileHandler fileHandler;

    public AbstractRequestHandler(ContentTypeResolver contentTypeResolver, FileHandler fileHandler) {
        this.contentTypeResolver = contentTypeResolver;
        this.fileHandler = fileHandler;
    }

    @Override
    public void validateRequest(ServerContext context, HttpRequest request) throws BadRequestException {
        if (!request.getHeaders()
                .containsKey(HttpHeader.HOST.getHeaderName())) {
            throw new BadRequestException("Missing host header. HTTP/1.1 condition violated");
        }
    }

    @Override
    public void setServerConfiguration(
            ServerContext context,
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
        String contentType = contentTypeResolver.resolve(request.getUrl().getContextPath());
        responseBuilder.addHeader(HttpHeader.CONTENT_TYPE.getHeaderName(), contentType);
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
