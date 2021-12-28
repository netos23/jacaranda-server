package ru.fbtw.jacarandaserver.sage.bean.annotation;

import ru.fbtw.jacarandaserver.sage.bean.internal.BeenComponent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@BeenComponent
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
}
