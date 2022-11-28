package ru.fbtw.jacarandaserver.sage.bean.internal.dependency;

import ru.fbtw.jacarandaserver.sage.bean.internal.BeanTemplate;

import java.util.Collections;
import java.util.List;

public class SingleBeanDependency implements BeanDependency {
	private final BeanTemplate template;

	public SingleBeanDependency(BeanTemplate template) {
		this.template = template;
	}

	@Override
	public Object supply() {
		return template.getBean();
	}

	@Override
	public List<BeanTemplate> getDependencies() {
		return Collections.singletonList(template);
	}
}
