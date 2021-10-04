package ru.fbtw.jacarandaserver.requests.enums;

import java.util.Optional;

public enum ContentType {
    // text
    HTML("text/html", ".+\\.(html|htm)"),
    CSS("text/css", ".+\\.css"),
    XML("text/xml", ".+\\.xml"),
    // application
    JSON("application/json", ".+\\.json"),
    JAVASCRIPT("application/javascript", ".+\\.js"),
    // image
    JPEG("image/jpeg", ".+\\.(jpg|jfif|jpe|jpeg)"),
    PNG("image/png", ".+\\.(png|PNG)"),
    GIF("image/gif", ".+\\.(gif|GIF)"),
    SVG("image/svg+xml", ".+\\.svg");

    private final String value;
    private final String matcher;


    ContentType(String value, String matcher) {
        this.value = value;
        this.matcher = matcher;
    }

    public String getValue() {
        return value;
    }

    public String getMatcher() {
        return matcher;
    }

    public static Optional<ContentType> getContentType(String path) {
        for (ContentType contentType : values()) {
            if (path.matches(contentType.matcher)) {
                return Optional.of(contentType);
            }
        }
        return Optional.empty();
    }
}
