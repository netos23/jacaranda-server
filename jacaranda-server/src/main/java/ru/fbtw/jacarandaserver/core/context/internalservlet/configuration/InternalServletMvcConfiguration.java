package ru.fbtw.jacarandaserver.core.context.internalservlet.configuration;

import ru.fbtw.jacarandaserver.sage.app.annotation.Configuration;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;

import java.util.Map;

@Configuration
public class InternalServletMvcConfiguration extends WebMvcConfiguration {
	public InternalServletMvcConfiguration() {
	}

	@Override
	public String getRootPath() {
		return "/";
	}

	@Override
	protected void configureResourceHandlers(Map<String, String> resourceHandlerRegistry) {
		super.configureResourceHandlers(resourceHandlerRegistry);
		resourceHandlerRegistry.put("/assets/", "/");
	}
}
