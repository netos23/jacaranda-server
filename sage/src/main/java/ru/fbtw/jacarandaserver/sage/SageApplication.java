package ru.fbtw.jacarandaserver.sage;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.sage.bean.internal.BeanRegistry;
import ru.fbtw.jacarandaserver.sage.bean.internal.BeanRegistryLoader;
import ru.fbtw.jacarandaserver.sage.bean.internal.exception.NoSuchBeanException;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;

public class SageApplication {

	public static SageApplication createSageApp() {
		BeanRegistry registry = new BeanRegistryLoader().loadBeans();
		WebMvcConfiguration webMvcConfiguration = null;
		try {
			webMvcConfiguration = registry.getAnyBeanByClass(WebMvcConfiguration.class);
		} catch (NoSuchBeanException e) {
			//todo: log there
		}

		webMvcConfiguration.getRequestPrefix();
		return null;
	}

	public void handle(HttpRequest request, HttpResponse.HttpResponseBuilder httpResponseBuilder){

	}

}
