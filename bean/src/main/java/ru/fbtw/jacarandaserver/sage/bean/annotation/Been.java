package ru.fbtw.jacarandaserver.sage.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@BeenComponent(source = ElementType.METHOD)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Been {
}
