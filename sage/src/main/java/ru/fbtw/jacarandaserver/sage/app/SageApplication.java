package ru.fbtw.jacarandaserver.sage.app;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;
import ru.fbtw.jacarandaserver.sage.controller.filter.AfterRequestFilterChain;
import ru.fbtw.jacarandaserver.sage.controller.filter.PreRequestFilterChain;
import ru.fbtw.jacarandaserver.sage.controller.hook.Hook;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMappingHandler;
import ru.fbtw.jacarandaserver.sage.exception.GenericExceptionHandlerRegistry;
import ru.fbtw.jacarandaserver.sage.exception.ResourceNotFoundException;

import java.util.Objects;

public final class SageApplication {
	private final WebMvcConfiguration mvcConfig;
	private final RequestMappingHandler requestMappingHandler;
	private final GenericExceptionHandlerRegistry exceptionHandlerRegistry;
	private final PreRequestFilterChain preRequestFilterChain;
	private final AfterRequestFilterChain afterRequestFilterChain;

	SageApplication(
			WebMvcConfiguration mvcConfig,
			RequestMappingHandler requestMappingHandler,
			GenericExceptionHandlerRegistry exceptionHandlerRegistry,
			PreRequestFilterChain preRequestFilterChain,
			AfterRequestFilterChain afterRequestFilterChain
	) {

		this.mvcConfig = mvcConfig;
		this.requestMappingHandler = requestMappingHandler;
		this.exceptionHandlerRegistry = exceptionHandlerRegistry;
		this.preRequestFilterChain = preRequestFilterChain;
		this.afterRequestFilterChain = afterRequestFilterChain;
	}

	public static SageApplicationBuilder builder() {
		return new SageApplicationBuilder();
	}

	public void handle(HttpRequest request, HttpResponse.HttpResponseBuilder httpResponseBuilder) {
		try {
			preRequestFilterChain.doFilter(request, httpResponseBuilder);

			String relativePath = getRelativePath(request);
			Hook hook = requestMappingHandler.resolve(relativePath);
			if (hook == null) {
				throw new ResourceNotFoundException(
						"Can`t resolve mapping for path: " + request.getUrl().getContextPath()
				);
			}

			afterRequestFilterChain.doFilter(request, httpResponseBuilder);
		} catch (Exception ex) {
			exceptionHandlerRegistry.handle(ex, httpResponseBuilder);
		}
	}

	private String getRelativePath(HttpRequest request) {
		String contextPath = request.getUrl().getContextPath();
		return contextPath.substring(mvcConfig.getRootPath().length() - 1);
	}

	public WebMvcConfiguration getMvcConfig() {
		return mvcConfig;
	}

	public RequestMappingHandler getRequestMappingHandler() {
		return requestMappingHandler;
	}

	public GenericExceptionHandlerRegistry getExceptionHandlerRegistry() {
		return exceptionHandlerRegistry;
	}

	public PreRequestFilterChain getPreRequestFilterChain() {
		return preRequestFilterChain;
	}

	public AfterRequestFilterChain getAfterRequestFilterChain() {
		return afterRequestFilterChain;
	}

	static class SageApplicationBuilder {
		private WebMvcConfiguration mvcConfig;
		private RequestMappingHandler requestMappingHandler;
		private GenericExceptionHandlerRegistry exceptionHandlerRegistry;
		private PreRequestFilterChain preRequestFilterChain;
		private AfterRequestFilterChain afterRequestFilterChain;

		private SageApplicationBuilder() {

		}

		public SageApplication build() {

			validateData();

			return new SageApplication(
					mvcConfig,
					requestMappingHandler,
					exceptionHandlerRegistry,
					preRequestFilterChain,
					afterRequestFilterChain
			);
		}

		private void validateData() {
			Objects.requireNonNull(mvcConfig);
			Objects.requireNonNull(requestMappingHandler);
			Objects.requireNonNull(exceptionHandlerRegistry);
			Objects.requireNonNull(preRequestFilterChain);
			Objects.requireNonNull(afterRequestFilterChain);
		}

		SageApplicationBuilder setMvcConfig(WebMvcConfiguration mvcConfig) {
			this.mvcConfig = mvcConfig;
			return this;
		}

		SageApplicationBuilder setRequestMappingHandler(RequestMappingHandler requestMappingHandler) {
			this.requestMappingHandler = requestMappingHandler;
			return this;
		}

		SageApplicationBuilder setExceptionHandlerRegistry(
				GenericExceptionHandlerRegistry exceptionHandlerRegistry
		) {
			this.exceptionHandlerRegistry = exceptionHandlerRegistry;
			return this;
		}

		SageApplicationBuilder setPreRequestFilterChain(PreRequestFilterChain preRequestFilterChain) {
			this.preRequestFilterChain = preRequestFilterChain;
			return this;
		}

		SageApplicationBuilder setAfterRequestFilterChain(AfterRequestFilterChain afterRequestFilterChain) {
			this.afterRequestFilterChain = afterRequestFilterChain;
			return this;
		}
	}
}
