package ru.fbtw.configuration.core;

import ru.fbtw.configuration.annotations.ConfigName;
import ru.fbtw.util.reflect.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ConfigurationLerp<T> {
	private final Supplier<T> targetFactory;
	private final Class<T> clazz;
	private final List<T> values;

	public static <T> ConfigurationLerp<T> createLerp(Class<T> clazz) throws NoSuchMethodException {
		Constructor<T> constructor = clazz.getConstructor();
		return new ConfigurationLerp<>(Reflections.emptyArgsConstructorToSupplier(constructor), clazz);
	}

	public ConfigurationLerp(Supplier<T> targetFactory, Class<T> clazz) {
		this.targetFactory = targetFactory;
		this.clazz = clazz;
		values = new ArrayList<>();
	}

	public ConfigurationLerp<T> add(T val) {
		Objects.requireNonNull(val);
		values.add(val);
		return this;
	}

	public ConfigurationLerp<T> addAll(List<T> val) {
		val.forEach(Objects::requireNonNull);
		values.addAll(val);
		return this;
	}


	public T apply() {
		T result = targetFactory.get();

		List<Field> markedFields = Reflections.getMarkedFields(clazz, ConfigName.class, false);

		for (Field f : markedFields) {
			Object mostCandidate = null;

			for (T candidate : values) {
				Object candidateValue = Reflections.getField(f, candidate);
				if (candidateValue != null) {
					mostCandidate = candidateValue;
				}
			}

			Reflections.setField(f, result, mostCandidate);
		}

		return result;
	}
}
