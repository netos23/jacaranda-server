package ru.fbtw.jacarandaserver.sage.bean.annotation;


import ru.fbtw.jacarandaserver.sage.bean.internal.BeenComponent;
import ru.fbtw.jacarandaserver.sage.controller.hook.AbstractHookFactory;
import ru.fbtw.jacarandaserver.sage.controller.hook.MvcHookFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BeenComponent
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	Class<? extends AbstractHookFactory> hookFactory() default MvcHookFactory.class;
}
