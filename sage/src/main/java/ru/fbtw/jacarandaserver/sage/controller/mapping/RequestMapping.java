package ru.fbtw.jacarandaserver.sage.controller.mapping;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;


public @interface RequestMapping {
	String value();

	HttpMethod[] allowedMethods() default {HttpMethod.GET, HttpMethod.POST};
}
