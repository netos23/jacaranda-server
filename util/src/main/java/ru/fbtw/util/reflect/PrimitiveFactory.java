package ru.fbtw.util.reflect;

import java.util.Objects;
import java.util.function.Function;

public class PrimitiveFactory {

	/**
	 * @param clazz
	 * @param s
	 * @return
	 */
	public static Object parse(Class<?> clazz, String s) {
		Function<String, Object> factory = getNullableFactory(clazz);

		if (factory == null) {
			String message = String.format("Missing factory for type: %s", clazz);
			throw new IllegalArgumentException(message);
		}

		return factory.apply(s);
	}

	/**
	 * @param clazz
	 * @return
	 */
	public static Function<String, Object> getNullableFactory(Class<?> clazz) {
		Function<String, Object> factory = getFactory(clazz);
		if (factory == null) {
			return null;
		}
		return getNullableFactory(factory);
	}

	/**
	 * @param factory
	 * @return
	 */
	public static Function<String, Object> getNullableFactory(Function<String, Object> factory) {
		Objects.requireNonNull(factory);

		return s -> {
			if (s == null || s.isEmpty()) {
				return null;
			}
			return factory.apply(s);
		};
	}

	/**
	 * Method wraps primitive parse method into factory Function.
	 * @throws NullPointerException when given clazz is null
	 * @param clazz  the class for which you need to get a factory
	 * @return  a factory that converts a string into a clazz object or null if specified factory isn`t present
	 */
	public static Function<String, Object> getFactory(Class<?> clazz) {
		Objects.requireNonNull(clazz);

		if (clazz.equals(Byte.class)) {
			return Byte::parseByte;
		} else if (clazz.equals(Short.class)) {
			return Short::parseShort;
		} else if (clazz.equals(Integer.class)) {
			return Integer::parseInt;
		} else if (clazz.equals(Long.class)) {
			return Long::parseLong;
		} else if (clazz.equals(Float.class)) {
			return Float::parseFloat;
		} else if (clazz.equals(Double.class)) {
			return Double::parseDouble;
		} else if (clazz.equals(Boolean.class)) {
			return Boolean::parseBoolean;
		} else if (clazz.equals(String.class)) {
			return Objects::requireNonNull;
		} else {
			return null;
		}
	}
}
