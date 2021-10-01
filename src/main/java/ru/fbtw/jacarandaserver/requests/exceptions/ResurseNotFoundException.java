package ru.fbtw.jacarandaserver.requests.exceptions;

public class ResurseNotFoundException extends BadRequestException {

    public ResurseNotFoundException() {
    }

    public ResurseNotFoundException(String message) {
        super(message);
    }

    public ResurseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
