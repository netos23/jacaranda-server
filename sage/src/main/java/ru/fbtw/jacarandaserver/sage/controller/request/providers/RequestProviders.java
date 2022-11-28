package ru.fbtw.jacarandaserver.sage.controller.request.providers;

import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.sage.controller.exception.IllegalAnnotationCombinationException;
import ru.fbtw.jacarandaserver.sage.controller.exception.MissingProviderException;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.AllRequestParams;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.RequestBody;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.RequestHeader;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.RequestParam;
import ru.fbtw.jacarandaserver.sage.view.Model;
import ru.fbtw.jacarandaserver.sage.view.resolvers.ContentTypeResolver;
import ru.fbtw.util.Pair;
import ru.fbtw.util.exceptions.MissingFactory;
import ru.fbtw.util.reflect.ClassUtils;
import ru.fbtw.util.reflect.PrimitiveFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequestProviders {

	private final Map<ContentType, ContentTypeResolver> resolverMap;

	public RequestProviders(List<ContentTypeResolver> resolvers) {
		resolverMap = resolvers.stream()
				.flatMap(resolver -> resolver.targetTypes()
						.stream()
						.map(t -> new Pair<>(t, resolver)))
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
	}

	public RequestProvider getRequestParamProvider(String param, Class<?> type) {
		return (request, responseBuilder, args) -> {
			List<String> rawParams = request.getUrl()
					.getQueryParams()
					.get(param);

			String rawParam = rawParams != null
					? rawParams.get(0)
					: null;

			Function<String, Object> factory = PrimitiveFactory.getFactory(type);
			if (factory == null) {
				throw new MissingFactory("Cant find factory");
			}

			return factory.apply(rawParam);
		};
	}

	public RequestProvider getRequestParamListProvider(String param, Class<?> type) {
		return (request, responseBuilder, args) -> {

			List<String> rawParams = request.getUrl()
					.getQueryParams()
					.get(param);

			Function<String, Object> factory = PrimitiveFactory.getFactory(type);
			if (factory == null) {
				throw new MissingFactory("Cant find factory");
			}

			return rawParams.stream()
					.map(factory)
					.collect(Collectors.toList());
		};
	}

	public RequestProvider getRequestProvider() {
		return (request, responseBuilder, args) -> request;
	}

	public RequestProvider getResponseBuilderProvider() {
		return (request, responseBuilder, args) -> responseBuilder;
	}

	public RequestProvider getAllRequestParamsProvider() {
		return (request, responseBuilder, args) -> request.getUrl().getQueryParams();
	}

	public RequestProvider getRequestBodyProvider(Class<?> type) {
		return (request, responseBuilder, args) -> {
			String header = request.getHeader(HttpHeader.CONTENT_TYPE.getHeaderName());
			Optional<ContentType> contentType = ContentType.getContentType(header);
			if (!contentType.isPresent()) {
				throw new BadRequestException("Missing or illegal content type");
			}
			return resolverMap.get(contentType.get()).resolve(request.getBody(), type);
		};
	}

	private RequestProvider getRequestHeaderProvider(String name, Class<?> headerType) {
		return (request, responseBuilder, args) -> {
			String header = request.getHeader(name);

			Function<String, Object> factory = PrimitiveFactory.getFactory(headerType);
			if (factory == null) {
				throw new MissingFactory("Cant find factory");
			}

			return factory.apply(header);
		};
	}

	public RequestProvider getModelProvider() {
		return (request, responseBuilder, args) -> args[0];
	}

	public RequestProvider getProvider(Type param, Annotation[] annotations) {
		RequestProvider provider = null;
		int providersCount = 0;
		if (param == Model.class) {
			providersCount++;
			provider = getModelProvider();
		}

		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == AllRequestParams.class && param instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) param;
				if (ClassUtils.isMap(parameterizedType.getRawType())
						&& parameterizedType.getActualTypeArguments()[0] == String.class
						&& parameterizedType.getActualTypeArguments()[1] == Object.class) {
					providersCount++;
					provider = getAllRequestParamsProvider();

				} else {
					throw new IllegalAnnotationCombinationException(
							"@AllQueryParams require signature Map<String, Object>"
					);
				}
			}

			if (annotation.annotationType() == RequestParam.class) {
				RequestParam requestParam = (RequestParam) annotation;
				if (param instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) param;
					Class<?> typeArgument;
					if (ClassUtils.isList(param)) {
						typeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
						provider = getRequestParamListProvider(requestParam.name(), typeArgument);
					} else {
						typeArgument = (Class<?>) parameterizedType.getRawType();
						provider = getRequestParamProvider(requestParam.name(), typeArgument);
					}
				} else {
					provider = getRequestParamProvider(requestParam.name(), (Class<?>) param);
				}
				providersCount++;
			}

			if (annotation.annotationType() == RequestHeader.class) {
				RequestHeader requestHeader = (RequestHeader) annotation;
				Class<?> headerType;
				if (param instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) param;
					headerType = (Class<?>) parameterizedType.getRawType();
				} else {
					headerType = (Class<?>) param;
				}
				providersCount++;
				provider = getRequestHeaderProvider(requestHeader.name(), headerType);
			}

			if (annotation.annotationType() == RequestBody.class) {
				Class<?> bodyType;
				if (param instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) param;
					bodyType = (Class<?>) parameterizedType.getRawType();
				} else {
					bodyType = (Class<?>) param;
				}
				provider = getRequestBodyProvider(bodyType);
				providersCount++;
			}
		}

		if (provider == null) {
			throw new MissingProviderException(param.toString());
		}

		if (providersCount != 1) {
			throw new IllegalAnnotationCombinationException(param.toString());
		}

		return provider;
	}


}
