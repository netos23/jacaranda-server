package ru.fbtw.jacarandaserver.sage.controller.filter.afterfilters;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.exceptions.BadRequestException;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ContentTypeRequestFilter implements PreRequestFilter {
	private final Set<String> supportedTypes;

	public ContentTypeRequestFilter() {
		supportedTypes = new HashSet<>();
		Collections.addAll(
				supportedTypes,
				ContentType.JSON.getValue(),
				ContentType.X_WWW_FORM_URLENCODED.getValue(),
				ContentType.JAVASCRIPT.getValue(),
				ContentType.HTML.getValue(),
				ContentType.CSS.getValue()
		);
	}

	@Override
	public void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder)
			throws BadRequestException {
		String requestBody = request.getBody();
		if (requestBody != null && !requestBody.isEmpty()) {
			String header = request.getHeader(HttpHeader.CONTENT_TYPE.getHeaderName());
			Optional<ContentType> optionalContentType = ContentType.getContentType(header);

			if (!optionalContentType.isPresent()
					|| !supportedTypes.contains(optionalContentType.get().getValue())
			) {
				throw new BadRequestException("Missing or unsupported content type");
			}
		}
	}
}
