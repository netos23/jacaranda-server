package ru.fbtw.jacarandaserver.requests;

import java.util.Locale;

public enum HttpMethod {
    GET,
    POST,
    UNSUPPORTED;

    public static HttpMethod valueOfOrDefault(String value, HttpMethod defaultVal) {
        HttpMethod method;
        try {
            method = HttpMethod.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            method = defaultVal;
        }
        return method;
    }
}
