package ru.fbtw.jacarandaserver.requests;

public class HttpRequestBuildException extends Exception {
    public HttpRequestBuildException(String message) {
        super(message);
    }

    public HttpRequestBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
