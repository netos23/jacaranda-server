package ru.fbtw.configuration;

import freemarker.template.TemplateExceptionHandler;
import ru.fbtw.ConverterApplication;
import ru.fbtw.jacarandaserver.sage.app.annotation.Configuration;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;

import java.io.File;
import java.io.IOException;

@Configuration
public class AsciiConverterMvcConfiguration extends WebMvcConfiguration {
	@Override
	protected void configureTemplates(freemarker.template.Configuration configuration) throws IOException {
		configuration.setClassForTemplateLoading(ConverterApplication.class, TEMPLATES);
//		configuration.setDirectoryForTemplateLoading(new File("./" + TEMPLATES));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		configuration.setWrapUncheckedExceptions(true);
		configuration.setFallbackOnNullLoopVariable(false);
	}
}
