package ru.fbtw.jacarandaserver.sage.bean.internal.dependency;

import ru.fbtw.jacarandaserver.sage.bean.internal.BeanTemplate;

import java.util.Collections;
import java.util.List;

public class ConfigurationValueBeanDependency implements BeanDependency{
	private final Object value;

	public ConfigurationValueBeanDependency(Object value) {
		this.value = value;
	}

	@Override
	public Object supply() {
		return value;
	}

	@Override
	public List<BeanTemplate> getDependencies() {
		return Collections.emptyList();
	}
}
