package ru.fbtw.jacarandaserver.requests.enums;

import java.util.Locale;

public enum HttpHeader {
    HOST("Host", Target.REQUEST),
    CONTENT_LENGTH("Content-Length", Target.BOTH),
    CONTENT_TYPE("Content-Type", Target.BOTH),
    CONNECTION("Connection", Target.BOTH),
    KEEP_ALIVE("Keep-alive", Target.BOTH),
    SERVER("Server", Target.RESPONSE);

    private final String headerName;
    private final Target target;

    HttpHeader(String headerName, Target target) {
        this.headerName = headerName;
        this.target = target;
    }

    public String getHeaderName() {
        return headerName;
    }

    public static HttpHeader getOrDefault(final String name, final HttpHeader defVal) {
        String canonicalName = name.replace('-', '_').toUpperCase(Locale.ROOT);

        HttpHeader result;
        try {
            result = HttpHeader.valueOf(canonicalName);
        } catch (Exception ex) {
            result = defVal;
        }

        return defVal;
    }

    public enum Target {
        REQUEST,
        RESPONSE,
        BOTH;
    }
}
