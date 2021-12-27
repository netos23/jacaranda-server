package ru.fbtw.configuration.core;

import ru.fbtw.configuration.annotations.Factory;

import ru.fbtw.util.exceptions.MissingFactory;
import ru.fbtw.util.reflect.PrimitiveFactory;
import ru.fbtw.util.reflect.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

public class AnnotationUtil {
	static Function<String, Object> extractFieldFactory(Factory factoryAnnotation)
			throws NoSuchMethodException {

		Method method = factoryAnnotation.clazz()
				.getDeclaredMethod(factoryAnnotation.method(), String.class);

		Reflections.requireStatic(method);

		return Reflections.staticMethodToFunction(method);
	}

	static Function<String, Object> getFieldFactory(Field field) {
		Factory factoryAnnotation = field.getAnnotation(Factory.class);
		Function<String, Object> factory = null;

		// try to get custom factory
		if (factoryAnnotation != null) {
			try {
				factory = AnnotationUtil.extractFieldFactory(factoryAnnotation);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

		// try to get primitive factory
		if (factory == null) {
			factory = PrimitiveFactory.getNullableFactory(field.getType());
		}

		// if both factories don`t present throws exception
		if (factory == null) {
			throw new MissingFactory(String.format("Factory for %s don`t present", field.getType()));
		}

		return factory;
	}
}
