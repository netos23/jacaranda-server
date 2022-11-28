package ru.fbtw.configuration.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.configuration.annotations.Configuration;
import ru.fbtw.configuration.exceptions.ConfigurationBuildException;

import java.io.IOException;
import java.util.List;

public class Configurations {
	private static final Logger logger = LoggerFactory.getLogger(Configurations.class);

	public static <T> T readConfig(String[] args, Class<T> clazz, T altConfig) {
		Configuration config = clazz.getAnnotation(Configuration.class);

		String filename = (config != null)
				? config.src()
				: Configuration.DEFAULT_SRC;

		String internalPath = "/" + filename;
		String externalPAth = "./" + filename;

		try {
			ConfigurationLerp<T> result = ConfigurationLerp.createLerp(clazz);

			List<T> defaultConfig = ConfigurationFactory.allFromDefaultConfig(clazz);
			result.addAll(defaultConfig);

			try {
				T internalConfig = ConfigurationFactory.fromResources(clazz, internalPath);
				result.add(internalConfig);
			} catch (IOException | ConfigurationBuildException | NoSuchMethodException e) {
				logger.error("Cant read internal config");
			}

			try {
				T externalConfig = ConfigurationFactory.fromFile(clazz, externalPAth);
				result.add(externalConfig);
			} catch (IOException | ConfigurationBuildException | NoSuchMethodException e) {
				e.printStackTrace();
				logger.error("Cant read external config");
			}

			try {
				T cliConfig = ConfigurationFactory.fromArgs(clazz, args);
				result.add(cliConfig);
			} catch (ConfigurationBuildException | NoSuchMethodException e) {
				e.printStackTrace();
				logger.error("Cant read cli config");
			}

			return result.apply();

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error during configuration read");
			return altConfig;
		}
	}


}
