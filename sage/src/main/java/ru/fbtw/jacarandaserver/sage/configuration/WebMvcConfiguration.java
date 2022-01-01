package ru.fbtw.jacarandaserver.sage.configuration;


import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebMvcConfiguration {

	public static final String TAMPLATES = "/templates/";
	private final Map<String, String> resourceHandlerRegistry;
	private final Configuration templateConfig;

	public WebMvcConfiguration() {
		resourceHandlerRegistry = new HashMap<>();
		templateConfig = new Configuration(Configuration.VERSION_2_3_31);
	}

	public void configure() throws IOException {
		configureResourceHandlers(resourceHandlerRegistry);
		configureTemplates(templateConfig);
	}

	protected void configureTemplates(Configuration configuration) throws IOException {
		configuration.setClassForTemplateLoading(WebMvcConfiguration.class, TAMPLATES);
		configuration.setDirectoryForTemplateLoading(new File("." + TAMPLATES));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		configuration.setWrapUncheckedExceptions(true);
		configuration.setFallbackOnNullLoopVariable(false);
	}

	protected void configureResourceHandlers(Map<String, String> resourceHandlerRegistry) {

	}

	public String getRootPath() {
		return "/";
	}


	public final Map<String, String> getResourceHandlerRegistry() {
		return resourceHandlerRegistry;
	}

	public final Configuration getTemplateConfig() {
		return templateConfig;
	}
}
