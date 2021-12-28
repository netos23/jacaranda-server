package ru.fbtw.jacarandaserver.sage.bean.internal.dependency;

import ru.fbtw.jacarandaserver.sage.bean.internal.BeanTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class ListBeanDependency implements BeanDependency{
	private final List<BeanTemplate> beanDependencies;

	public ListBeanDependency(List<BeanTemplate> beanDependencies) {
		this.beanDependencies = beanDependencies;
	}


	@Override
	public Object supply() {
		return beanDependencies.stream()
				.map(BeanTemplate::getBean)
				.collect(Collectors.toList());
	}

	@Override
	public List<BeanTemplate> getDependencies() {
		return beanDependencies;
	}
}
