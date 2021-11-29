package ru.fbtw.jacarandaserver.util;

import ru.fbtw.jacarandaserver.configuration.annotations.Factory;
import ru.fbtw.jacarandaserver.util.exceptions.NonStaticException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Reflections {
	public static Object getStaticField(Field f) {
		try {
			return f.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getField(Field f, Object obj) {
		try {
			f.setAccessible(true);
			Object val = f.get(obj);
			f.setAccessible(false);
			return val;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setField(Field f, Object obj, Object val) {
		try {
			f.setAccessible(true);
			f.set(obj, val);
			f.setAccessible(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static List<Field> getMarkedFields(
			Class<?> clazz,
			Class<? extends Annotation> annotation,
			boolean isStatic
	) {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(f -> hasAnnotation(annotation, f))
				.filter(f -> !isStatic || isStatic(f))
				.collect(Collectors.toList());
	}

	public static boolean hasAnnotation(Class<? extends Annotation> annotation, Field field) {
		return field.getAnnotation(annotation) != null;
	}

	public static boolean hasAnnotation(Class<? extends Annotation> annotation, Class<?> clazz) {
		return clazz.getAnnotation(annotation) != null;
	}

	public static boolean isStatic(Field f) {
		return Modifier.isStatic(f.getModifiers());
	}

	public static boolean isStatic(Method m) {
		return Modifier.isStatic(m.getModifiers());
	}

	public static void requireStatic(Field f) {
		if (!isStatic(f)) {
			throw new NonStaticException();
		}
	}

	public static void requireStatic(Method m) {
		if (!isStatic(m)) {
			throw new NonStaticException();
		}
	}

	/**
	 * Wrap method inside function with exception handler
	 *
	 * @param method factory method declared in class marked as {@link Factory}
	 * @return factory
	 * @see Factory
	 * @see Method
	 * @see Function
	 */
	public static Function<String, Object> staticMethodToFunction(Method method) {
		requireStatic(method);

		return s -> {
			try {
				return method.invoke(null, s);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return null;
		};
	}

	public static <T> Supplier<T> emptyArgsConstructorToSupplier(Class<T> clazz) throws NoSuchMethodException {
		return emptyArgsConstructorToSupplier(clazz.getConstructor());
	}

	public static <T> Supplier<T> emptyArgsConstructorToSupplier(Constructor<T> constructor) {
		return () -> {
			try {
				return constructor.newInstance();
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
			return null;
		};
	}

	public static boolean hasInterface(Class<?> clazz, Class<?> interfaceClass) {
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> servletInterface : interfaces) {
			if (servletInterface.equals(interfaceClass)) {
				return true;
			}
		}
		return false;
	}


}
