package ru.fbtw.jacarandaserver.sage.view.resolvers;

import com.google.gson.Gson;
import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;

import java.util.Collections;
import java.util.Set;

public class ApplicationJsonContentTypeResolver implements ContentTypeResolver {

	private final Set<ContentType> contentTypes;
	private final Gson gson;

	public ApplicationJsonContentTypeResolver() {
		contentTypes = Collections.singleton(ContentType.JSON);
		gson = new Gson();
	}

	@Override
	public Object resolve(String rawData, Class<?> target) {
		try {
			return gson.fromJson(rawData, target);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Exception during json parse", ex);
		}
	}

	@Override
	public Set<ContentType> targetTypes() {
		return contentTypes;
	}
}
