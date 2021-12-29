package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProviders;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.Function;

public abstract class AbstractHookFactory {
	protected final RequestProviders providers;

	protected AbstractHookFactory(RequestProviders providers) {
		this.providers = providers;
	}

	public abstract Hook createHook(Object controller, Method hookCandidate, HttpMethod method);

	protected RequestProvider[] getRequestProviders(Method hookCandidate) {
		Type[] parameterTypes = hookCandidate.getGenericParameterTypes();
		Annotation[][] parameterAnnotations = hookCandidate.getParameterAnnotations();
		RequestProvider[] requestProviders = new RequestProvider[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			requestProviders[i] = providers.getProvider(
					parameterTypes[i],
					parameterAnnotations[i]
			);
		}
		return requestProviders;
	}

	protected Function<Object[], Object> methodToCallback(Method method, Object controller) {
		return args -> {
			try {
				method.setAccessible(true);
				return method.invoke(controller, args);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException("Can`t assemble hook", e);
			}
		};
	}
}
