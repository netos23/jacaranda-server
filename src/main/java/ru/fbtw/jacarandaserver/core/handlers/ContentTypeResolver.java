package ru.fbtw.jacarandaserver.core.handlers;

import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;

import java.util.Optional;

public class ContentTypeResolver {
    public String resolve(String src) {
        Optional<ContentType> contentType = ContentType.getContentType(src);
        if (contentType.isPresent()) {
            return contentType.get().getValue();
        } else {
            return ContentType.HTML.getValue();
        }
    }
}
