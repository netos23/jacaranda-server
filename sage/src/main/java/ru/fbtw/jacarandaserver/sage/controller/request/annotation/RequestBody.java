package ru.fbtw.jacarandaserver.sage.controller.request.annotation;

import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBody {
	ContentType contentType() default ContentType.HTML;
	boolean required() default false;
}
