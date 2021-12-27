package ru.fbtw.configuration.annotations;


import ru.fbtw.util.reflect.Reflections;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Factory {
	Class<?> clazz();
	String method();
}
