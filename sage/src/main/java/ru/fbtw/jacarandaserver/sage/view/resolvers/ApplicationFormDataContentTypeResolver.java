package ru.fbtw.jacarandaserver.sage.view.resolvers;

import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationFormDataContentTypeResolver  implements ContentTypeResolver{

	private final Set<ContentType> contentTypes;

	public ApplicationFormDataContentTypeResolver() {
		contentTypes = Collections.singleton(ContentType.X_WWW_FORM_URLENCODED);
	}

	@Override
	public Object resolve(String rawData, Class<?> target) {
		return null;
	}

	@Override
	public Set<ContentType> targetTypes() {
		return contentTypes;
	}
}
