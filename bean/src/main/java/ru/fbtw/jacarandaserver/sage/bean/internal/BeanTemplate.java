package ru.fbtw.jacarandaserver.sage.bean.internal;

import ru.fbtw.jacarandaserver.sage.bean.internal.dependency.BeanInjector;

import java.lang.annotation.Annotation;
import java.util.List;

public class BeanTemplate {
	private Object bean;
	private final Class<?> clazz;
	private final Class<? extends Annotation> beanType;
	private BeanInjector injector;

	public BeanTemplate(Class<?> clazz, Class<? extends Annotation> beanType) {
		this.clazz = clazz;
		this.beanType = beanType;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public boolean isNotInitialized() {
		return bean == null;
	}

	public boolean hasInjector() {
		return injector != null;
	}

	public void setInjector(BeanInjector injector) {
		this.injector = injector;
	}

	public List<BeanTemplate> getDependencies() {
		return injector.getDependencies();
	}

	public Object getBean() {
		if (isNotInitialized()) {
			bean = injector.inject(clazz.getConstructors()[0]);
		}

		return bean;
	}

	public Class<? extends Annotation> getBeanType() {
		return beanType;
	}
}
