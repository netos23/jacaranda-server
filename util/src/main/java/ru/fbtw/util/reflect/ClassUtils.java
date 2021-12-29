package ru.fbtw.util.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ClassUtils {
	public static void expandTypes(Class<?> parent, Consumer<Class<?>> classConsumer) {
		if (parent == null) {
			return;
		}
		classConsumer.accept(parent);

		expandTypes(parent.getSuperclass(), classConsumer);
		for (Class<?> interfaceType : parent.getInterfaces()) {
			expandTypes(interfaceType, classConsumer);
		}
	}

	public static boolean isList(Type param) {
		Set<Class<?>> parents = getParents((ParameterizedType) param);
		return parents.contains(List.class);
	}

	public static boolean isMap(Type param) {
		Set<Class<?>> parents = getParents((ParameterizedType) param);
		return parents.contains(Map.class);
	}

	private static Set<Class<?>> getParents(ParameterizedType param) {
		Set<Class<?>> parents = new HashSet<>();
		ClassUtils.expandTypes((Class<?>) param.getRawType(), parents::add);
		return parents;
	}
}
