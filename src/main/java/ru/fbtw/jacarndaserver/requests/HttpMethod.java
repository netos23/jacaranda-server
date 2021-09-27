package ru.fbtw.jacarndaserver.requests;

public enum HttpMethod {
    GET,
    UNSUPPORTED;

    public static HttpMethod valueOfOrDefault(String value, HttpMethod defaultVal) {
        HttpMethod method;
        try {
            method = HttpMethod.valueOf(value);
        } catch (IllegalArgumentException ex) {
            method = defaultVal;
        }
        return method;
    }
}
