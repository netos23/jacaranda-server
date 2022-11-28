package ru.fbtw.configuration.core;

import ru.fbtw.util.Pair;
import ru.fbtw.util.exceptions.MissingFactory;
import ru.fbtw.util.io.IOUtils;
import ru.fbtw.util.reflect.PrimitiveFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConfigurationReader {

	private static final String CLI_PATTERN = "-[a-zA-Z].*=.+";
	private static final String FILE_PATTERN = "[a-zA-Z].*=.+";

	/**
	 * Read matching Strings from file and convert them into Map
	 *
	 * @param file data source
	 * @return Map of keys and values matching specified pattern
	 * @throws IOException if specified file don`t found
	 */
	static Map<String, String> readFileConfig(final File file) throws IOException {
		return readInputStreamConfig(Files.newInputStream(file.toPath()));
	}

	/**
	 * Read matching Strings from inputStream and convert them into Map
	 *
	 * @param inputStream data source
	 * @return Map of keys and values matching specified pattern
	 * @throws IOException if specified file don`t found
	 */
	static Map<String, String> readInputStreamConfig(final InputStream inputStream) throws IOException {
		String[] args = new String(IOUtils.readAllBytes(inputStream))
				.split("\n");

		return readConfig(args, FILE_PATTERN, 0);
	}

	/**
	 * Read CLI args and convert them into Map
	 *
	 * @param args cli args
	 * @return Map of keys and values matching specified pattern
	 */
	static Map<String, String> readCliConfig(final String[] args) {
		return readConfig(args, CLI_PATTERN, 1);
	}

	/**
	 * @param args    separated flags
	 * @param pattern pattern to filter flags
	 * @return Map of keys and values matching specified pattern
	 */
	private static Map<String, String> readConfig(
			final String[] args,
			final String pattern,
			final int offset
	) {
		return Arrays.stream(args)
				.filter(Objects::nonNull)
				.filter(v -> v.matches(pattern))
				.map(argToPair(offset))
				.collect(Collectors.toMap(Pair<String, String>::getKey, Pair<String, String>::getValue));
	}

	private static Function<String, Pair<String, String>> argToPair(int offset) {
		return s -> {
			int splitIndex = s.indexOf("=");

			String key = s.substring(offset, splitIndex);
			String value = s.substring(splitIndex + 1);

			return new Pair<>(key, value);
		};
	}

	public static ConfigurationReader formInputStream(InputStream inputStream) throws IOException {
		Map<String, String> config = readInputStreamConfig(inputStream);
		return new ConfigurationReader(config);
	}

	public static ConfigurationReader formFile(File file) throws IOException {
		Map<String, String> config = readFileConfig(file);
		return new ConfigurationReader(config);
	}


	public static ConfigurationReader fromArgs(String[] args) {
		Map<String, String> config = readCliConfig(args);
		return new ConfigurationReader(config);
	}

	private final Map<String, String> configMap;

	private ConfigurationReader(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	public String readValue(String name) {
		return configMap.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T readValue(String name, Class<T> clazz) {
		Function<String, Object> factory = PrimitiveFactory.getFactory(clazz);

		if (!Objects.nonNull(factory)) {
			throw new MissingFactory(String.format("Cant find factory for: %s", clazz));
		}

		return (T) readValue(name, factory);
	}

	public <T> T readValue(String name, Function<String, T> factory) {
		return factory.apply(name);
	}

	public Map<String, String> getSubConfig(List<String> names) {
		return names.stream()
				.map(n -> new Pair<>(n, configMap.get(n)))
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
	}


}
