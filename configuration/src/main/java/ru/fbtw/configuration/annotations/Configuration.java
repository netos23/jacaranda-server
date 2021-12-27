package ru.fbtw.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
	String DEFAULT_CONFIG_NAME = "appconfig";
	String DEFAULT_SRC = "application.properties";

	String name() default DEFAULT_CONFIG_NAME;

	String prefix() default "";

	String src() default DEFAULT_SRC;
}
