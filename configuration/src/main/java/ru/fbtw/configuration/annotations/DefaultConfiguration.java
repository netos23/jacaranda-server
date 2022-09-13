package ru.fbtw.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that describe static instance of {@link Configuration} which used if configuration files missing
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultConfiguration {
	/**
	 * @return target configuration name
	 */
	String target() default Configuration.DEFAULT_CONFIG_NAME;
}
