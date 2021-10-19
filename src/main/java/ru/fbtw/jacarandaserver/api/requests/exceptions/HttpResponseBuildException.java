package ru.fbtw.jacarandaserver.api.requests.exceptions;

public class HttpResponseBuildException extends BadRequestException {
    public HttpResponseBuildException() {
    }

    public HttpResponseBuildException(String message) {
        super(message);
    }

    public HttpResponseBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
