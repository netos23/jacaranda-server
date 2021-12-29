package ru.fbtw.jacarandaserver.sage.controller.request.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHeader {
	String name() default "";
	String defaultValue() default "";
	boolean required() default false;
}
