package ru.fbtw.jacarandaserver.sage.bean.internal.dependency;

import ru.fbtw.jacarandaserver.sage.bean.internal.BeanTemplate;

import java.util.List;

public interface BeanDependency {
	Object supply();
	List<BeanTemplate> getDependencies();
}
