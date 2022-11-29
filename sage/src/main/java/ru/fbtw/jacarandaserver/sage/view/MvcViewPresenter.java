package ru.fbtw.jacarandaserver.sage.view;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;
import ru.fbtw.jacarandaserver.sage.view.exception.ViewBuildException;
import ru.fbtw.jacarandaserver.sage.view.exception.ViewNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
@Component
public class MvcViewPresenter implements ViewPresenter<DataModelWithView> {

	private final Configuration templateConfig;

	public MvcViewPresenter(WebMvcConfiguration mvcConfig) {
		this.templateConfig = mvcConfig.getTemplateConfig();
	}

	@Override
	public byte[] present(DataModelWithView content) {
		try {
			Template template = templateConfig.getTemplate(content.getView() + ".ftlh");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Writer writer = new PrintWriter(byteArrayOutputStream);
			template.process(content.getModel().getInternalModel(), writer);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ViewNotFoundException();
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new ViewBuildException();
		}
	}
}
