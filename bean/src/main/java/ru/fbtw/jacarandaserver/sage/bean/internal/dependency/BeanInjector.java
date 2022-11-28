package ru.fbtw.jacarandaserver.sage.bean.internal.dependency;

import ru.fbtw.jacarandaserver.sage.bean.internal.BeanTemplate;
import ru.fbtw.jacarandaserver.sage.bean.internal.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanInjector {
	private final List<BeanDependency> beanDependencies;

	public static BeanInjectorBuilder builder() {
		return new BeanInjector().new BeanInjectorBuilder();
	}

	private BeanInjector() {
		this.beanDependencies = new ArrayList<>();
	}

	public Object inject(Constructor<?> constructor) {
		Object[] args = beanDependencies.stream()
				.map(BeanDependency::supply)
				.toArray();
		constructor.setAccessible(true);
		try {
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new BeanCreationException(constructor.getDeclaringClass(), e);
		}
	}

	public List<BeanTemplate> getDependencies() {
		return beanDependencies.stream()
				.flatMap(beanDependency -> beanDependency.getDependencies().stream())
				.collect(Collectors.toList());
	}

	public class BeanInjectorBuilder {
		private BeanInjectorBuilder() {
		}

		public BeanInjectorBuilder addDependency(BeanDependency dependency) {
			beanDependencies.add(dependency);
			return this;
		}

		public BeanInjector build() {
			return BeanInjector.this;
		}
	}
}
