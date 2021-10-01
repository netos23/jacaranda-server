package ru.fbtw.jacarandaserver.requests.exceptions;

public class NotImplementedException extends BadRequestException {
    public NotImplementedException() {
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
