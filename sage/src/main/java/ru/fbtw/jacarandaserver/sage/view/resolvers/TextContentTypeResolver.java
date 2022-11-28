package ru.fbtw.jacarandaserver.sage.view.resolvers;

import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TextContentTypeResolver implements ContentTypeResolver {
	private final Set<ContentType> contentTypes;

	public TextContentTypeResolver() {
		contentTypes = new HashSet<>();
		Collections.addAll(
				contentTypes,
				ContentType.CSS,
				ContentType.HTML,
				ContentType.JAVASCRIPT
		);
	}

	@Override
	public Object resolve(String rawData, Class<?> target) {
		if (target != String.class) {
			throw new IllegalArgumentException(String.format("Text content type resolver can`t convert: %s", target));
		}
		return rawData;
	}

	@Override
	public Set<ContentType> targetTypes() {
		return contentTypes;
	}
}
