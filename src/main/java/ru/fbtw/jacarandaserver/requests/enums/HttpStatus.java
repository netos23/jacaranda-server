package ru.fbtw.jacarandaserver.requests.enums;

public enum HttpStatus {
    OK(200),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
