package ru.fbtw.configuration.core;

import ru.fbtw.configuration.annotations.ConfigName;
import ru.fbtw.configuration.annotations.Configuration;
import ru.fbtw.configuration.annotations.DefaultConfiguration;
import ru.fbtw.configuration.exceptions.ConfigurationBuildException;
import ru.fbtw.configuration.exceptions.MissingConfiguration;
import ru.fbtw.util.reflect.Reflections;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConfigurationFactory {

	public static <T> T fromArgs(Class<T> clazz, String[] args)
			throws NoSuchMethodException, ConfigurationBuildException {
		ConfigurationReader reader = ConfigurationReader.fromArgs(args);
		return fromReader(clazz, reader);
	}

	public static <T> T fromFile(Class<T> clazz, String path)
			throws IOException, NoSuchMethodException, ConfigurationBuildException {
		File file = new File(path);
		return fromFile(clazz, file);
	}

	public static <T> T fromResources(Class<T> clazz, String name)
			throws IOException, NoSuchMethodException, ConfigurationBuildException {

		try (InputStream inputStream = clazz.getResourceAsStream(name)) {
			ConfigurationReader reader = ConfigurationReader.formInputStream(inputStream);
			return fromReader(clazz, reader);
		}

	}

	public static <T> T fromFile(Class<T> clazz, File file)
			throws IOException, NoSuchMethodException, ConfigurationBuildException {
		ConfigurationReader reader = ConfigurationReader.formFile(file);
		return fromReader(clazz, reader);
	}

	public static <T> T fromReader(Class<T> clazz, ConfigurationReader reader)
			throws NoSuchMethodException, ConfigurationBuildException {
		Configuration configMeta = clazz.getAnnotation(Configuration.class);

		if (configMeta == null) {
			throw new MissingConfiguration();
		}

		List<Field> markedFields = Reflections.getMarkedFields(clazz, ConfigName.class, false);
		T config = Reflections.emptyArgsConstructorToSupplier(clazz).get();
		if (config == null) {
			throw new ConfigurationBuildException();
		}

		for (Field field : markedFields) {
			ConfigName configNameMeta = field.getAnnotation(ConfigName.class);
			String value = reader.readValue(configNameMeta.name());
			if (value == null) {
				value = configNameMeta.value();
			}

			Function<String, Object> fieldFactory = AnnotationUtil.getFieldFactory(field);

			Reflections.setField(field, config, fieldFactory.apply(value));

		}

		return config;
	}

	public static <T> T fromDefaultConfig(Class<T> clazz) {
		return fromDefaultConfig(clazz, clazz);
	}

	public static <T> T fromDefaultConfig(Class<T> clazz, Class<?> location) {
		List<T> configs = allFromDefaultConfig(clazz, location);
		return configs.isEmpty() ? null : configs.get(0);
	}

	public static <T> List<T> allFromDefaultConfig(Class<T> clazz) {
		return allFromDefaultConfig(clazz, clazz);
	}

	/**
	 * This method gets static instances of the configuration class located inside them
	 * and marked as the {@link DefaultConfiguration}. It`s ignore non-static instances
	 * and null fields
	 *
	 * @param clazz    target configuration class descriptor
	 * @param location location of default configuration
	 * @param <T>      target configuration type
	 * @return list of default configuration instances
	 * @see DefaultConfiguration
	 */
	public static <T> List<T> allFromDefaultConfig(Class<T> clazz, Class<?> location) {
		return Reflections.getMarkedFields(location, DefaultConfiguration.class, true)
				.stream()
				.filter(f -> f.getType().equals(clazz))
				.map(Reflections::getStaticField)
				.filter(Objects::nonNull)
				.map(clazz::cast)
				.collect(Collectors.toList());
	}
}
