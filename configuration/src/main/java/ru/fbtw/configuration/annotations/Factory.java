package ru.fbtw.configuration.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that describe a static factory method for deserializing objects
 * <br>
 * Motivation: Configuration library can deserialize some primitive types from configuration file,
 * but you can describe a custom {@link Factory} that will deserialize any class
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Factory {
	/**
	 * @return location of static factory method
	 */
	Class<?> clazz();

	/**
	 * @return target static factory method
	 */
	String method();
}
