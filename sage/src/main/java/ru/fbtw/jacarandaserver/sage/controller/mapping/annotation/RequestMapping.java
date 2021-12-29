package ru.fbtw.jacarandaserver.sage.controller.mapping.annotation;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String value();

	HttpMethod[] allowedMethods() default {HttpMethod.GET, HttpMethod.POST};
}
