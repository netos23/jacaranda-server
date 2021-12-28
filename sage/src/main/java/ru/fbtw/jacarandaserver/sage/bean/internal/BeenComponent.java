package ru.fbtw.jacarandaserver.sage.bean.internal;

import ru.fbtw.util.reflect.PrimitiveFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeenComponent {
	ElementType source() default ElementType.TYPE;

	Class<?> beanFactory() default PrimitiveFactory.class;
}
