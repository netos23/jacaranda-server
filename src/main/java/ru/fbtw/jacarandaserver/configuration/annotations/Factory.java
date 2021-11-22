package ru.fbtw.jacarandaserver.configuration.annotations;


import ru.fbtw.jacarandaserver.util.Reflections;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.function.Function;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Factory {
	Class<?> clazz();
	String method();
}
