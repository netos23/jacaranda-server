package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.view.FilePresenter;

import java.util.Collections;
import java.util.Set;

public class ResourceHandlerHook implements Hook {

	private final Set<HttpMethod> method;
	private final FilePresenter presenter;
	private final String resourceRoot;
	private final String pathRoot;

	public ResourceHandlerHook(
			FilePresenter presenter,
			String resourceRoot,
			String pathRoot
	) {
		this.presenter = presenter;
		this.resourceRoot = resourceRoot;
		this.pathRoot = pathRoot;
		method = Collections.singleton(HttpMethod.GET);
	}

	@Override
	public void hang(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		String contextPath = request.getUrl().getContextPath();
		String relativePath = contextPath.substring(pathRoot.length());
		byte[] file = presenter.present("." + resourceRoot + relativePath);
		responseBuilder.setBody(file);
		responseBuilder.addHeader(HttpHeader.CONTENT_TYPE.getHeaderName(), ContentType.resolve(relativePath));
	}

	@Override
	public Set<HttpMethod> getSupportedMethods() {
		return method;
	}
}
