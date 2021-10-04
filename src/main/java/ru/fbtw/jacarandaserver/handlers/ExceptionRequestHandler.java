package ru.fbtw.jacarandaserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.io.FileReader;
import ru.fbtw.jacarandaserver.requests.HttpRequest;
import ru.fbtw.jacarandaserver.requests.HttpResponse;
import ru.fbtw.jacarandaserver.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.requests.enums.HttpStatus;
import ru.fbtw.jacarandaserver.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.requests.exceptions.HttpRequestBuildException;
import ru.fbtw.jacarandaserver.requests.exceptions.NotImplementedException;
import ru.fbtw.jacarandaserver.requests.exceptions.ResurseNotFoundException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExceptionRequestHandler extends AbstractRequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionRequestHandler.class);

    private final ServerContext context;

    public ExceptionRequestHandler(
            ContentTypeResolver contentTypeResolver,
            FileHandler fileHandler,
            ServerContext context
    ) {
        super(contentTypeResolver, fileHandler);
        this.context = context;
    }

    public HttpResponse handle(HttpRequest request, Exception ex) {
        HttpResponse.HttpResponseBuilder responseBuilder = HttpResponse.newBuilder();
        super.setServerConfiguration(context, request, responseBuilder);

        HttpStatus status;
        if (ex instanceof ResurseNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof NotImplementedException) {
            status = HttpStatus.NOT_IMPLEMENTED;
        } else if (ex instanceof BadRequestException || ex instanceof HttpRequestBuildException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        try {
            String template = FileReader.readFile(new File(context.getErrorTemplate()));
            String body = String.format(template, status.getCode(), status.name());
            byte[] byteBody = body.getBytes(StandardCharsets.UTF_8);

            responseBuilder.setBody(byteBody);
            responseBuilder.addHeader(HttpHeader.CONTENT_LENGTH.getHeaderName(), Integer.toString(byteBody.length));
        } catch (IOException e) {
            logger.warn("Cant load error template");
            e.printStackTrace();
        }

        responseBuilder.setStatus(status);

        return responseBuilder.build();
    }
}
