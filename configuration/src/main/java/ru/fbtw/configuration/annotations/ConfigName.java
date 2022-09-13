package ru.fbtw.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that describe param name in config file linked with target field,
 * and default value if that name missing in configuration file.
 *
 * @see Factory
 * @see DefaultConfiguration
 * @see Configuration
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigName {

	/**
	 * @return param name in config file linked with target field
	 */
	String name();

	/**
	 * @return default field value
	 */
	String value() default "";
}
