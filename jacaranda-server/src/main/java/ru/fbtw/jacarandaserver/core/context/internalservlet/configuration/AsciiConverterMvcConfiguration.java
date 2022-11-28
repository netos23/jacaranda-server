package ru.fbtw.jacarandaserver.core.context.internalservlet.configuration;

import ru.fbtw.jacarandaserver.sage.bean.annotation.Configuration;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;

import java.util.Map;

@Configuration
public class AsciiConverterMvcConfiguration extends WebMvcConfiguration {
	public AsciiConverterMvcConfiguration() {
	}

	@Override
	public String getRootPath() {
		return "/";
	}

	@Override
	protected void configureResourceHandlers(Map<String, String> resourceHandlerRegistry) {
		super.configureResourceHandlers(resourceHandlerRegistry);
		resourceHandlerRegistry.put("/resources/", "/");
	}
}
