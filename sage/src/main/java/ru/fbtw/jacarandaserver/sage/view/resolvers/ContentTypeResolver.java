package ru.fbtw.jacarandaserver.sage.view.resolvers;

import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;

import java.util.List;
import java.util.Set;

public interface ContentTypeResolver {

	Object resolve(String rawData, Class<?> target);

	Set<ContentType> targetTypes();
}
