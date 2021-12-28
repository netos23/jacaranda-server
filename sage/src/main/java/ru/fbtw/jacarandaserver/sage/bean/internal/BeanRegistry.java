package ru.fbtw.jacarandaserver.sage.bean.internal;

import ru.fbtw.jacarandaserver.sage.bean.internal.exception.NoSuchBeanException;
import ru.fbtw.util.ListUtils;
import ru.fbtw.util.Pair;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public final class BeanRegistry {
	private final BeanRegistryEntry classEntry;
	private final BeanRegistryEntry annotationEntry;


	static BeanRegistry buildBeanRegistry(Map<Class<?>, List<BeanTemplate>> beanMap) {
		BeanRegistryEntry classEntity = getClassBeanRegistryEntry(beanMap);
		BeanRegistryEntry annotationEntry = buildAnnotationBeanRegistryEntry(beanMap);
		return new BeanRegistry(classEntity, annotationEntry);
	}

	private BeanRegistry(
			BeanRegistryEntry classEntry,
			BeanRegistryEntry annotationEntry
	) {
		this.classEntry = classEntry;
		this.annotationEntry = annotationEntry;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAllBeansByClass(Class<T> clazz) throws NoSuchBeanException{
		return classEntry.getAllBeansByClass(clazz)
				.stream()
				.map(obj -> (T) obj)
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public <T> T getAnyBeanByClass(Class<T> clazz) throws NoSuchBeanException{
		return (T) classEntry.getAnyByClass(clazz);
	}

	public List<Object> getAllBeansByAnnotatedType(Class<? extends Annotation> annotation) throws NoSuchBeanException {
		return annotationEntry.getAllBeansByClass(annotation);
	}

	public Object getAnyBeanByAnnotatedType(Class<? extends Annotation> annotation) throws NoSuchBeanException {
		return annotationEntry.getAnyByClass(annotation);
	}

	private static BeanRegistryEntry getClassBeanRegistryEntry(Map<Class<?>, List<BeanTemplate>> beanMap) {
		Map<Class<?>, List<Object>> classEntryMap = beanMap.entrySet()
				.stream()
				.map(entry -> new Pair<>(
						entry.getKey(),
						entry.getValue()
								.stream()
								.map(BeanTemplate::getBean)
								.collect(Collectors.toList())
				)).collect(Collectors.toMap(
						Pair::getKey,
						Pair::getValue
				));
		return new BeanRegistryEntry(classEntryMap);
	}

	private static BeanRegistryEntry buildAnnotationBeanRegistryEntry(
			Map<Class<?>, List<BeanTemplate>> beans
	) {
		Map<Class<?>, List<Object>> beanTypeMap = beans.get(Object.class)
				.stream()
				.collect(Collectors.toMap(
						BeanTemplate::getBeanType,
						beanTemplate -> Collections.singletonList(beanTemplate.getBean()),
						ListUtils::mergeLists
				));

		return new BeanRegistryEntry(beanTypeMap);
	}

	private static final class BeanRegistryEntry {
		private final Map<Class<?>, List<Object>> storage;

		private BeanRegistryEntry(Map<Class<?>, List<Object>> storage) {
			this.storage = storage;
		}

		public Object getAnyByClass(Class<?> clazz) throws NoSuchBeanException{
			List<Object> beanList = getAllBeansByClass(clazz);
			return beanList.get(0);
		}

		private List<Object> getAllBeansByClass(Class<?> clazz) throws NoSuchBeanException {
			List<Object> beanList = storage.get(clazz);
			if (beanList == null || beanList.isEmpty()) {
				throw new NoSuchBeanException(clazz);
			}
			return beanList;
		}
	}
}
