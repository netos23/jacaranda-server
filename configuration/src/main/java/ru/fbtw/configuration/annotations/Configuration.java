package ru.fbtw.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that describe marked classes mapped on configuration files
 *
 * @see ConfigName
 * @see DefaultConfiguration
 * @see Factory
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
	/**
	 * Configuration name, that used by default for configuration injections
	 */
	String DEFAULT_CONFIG_NAME = "appconfig";

	/**
	 * Configuration name, that used by default for configuration file
	 */
	String DEFAULT_SRC = "application.properties";

	/**
	 * @return configuration name, that used for configuration injections. Default DEFAULT_CONFIG_NAME
	 */
	String name() default DEFAULT_CONFIG_NAME;

	/**
	 * @return configuration name prefix for all names, described by {@link ConfigName}
	 */
	String prefix() default "";

	/**
	 * @return configuration name, that used by default for configuration file
	 */
	String src() default DEFAULT_SRC;
}
