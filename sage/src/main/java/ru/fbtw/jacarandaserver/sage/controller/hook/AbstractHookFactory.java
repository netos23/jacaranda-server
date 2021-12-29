package ru.fbtw.jacarandaserver.sage.controller.hook;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMappingHandler;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.GetMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.PostMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.RequestMapping;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProviders;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractHookFactory {
	protected final RequestProviders providers;
	private final RequestMappingHandler mappingHandler;

	protected AbstractHookFactory(RequestProviders providers, RequestMappingHandler mappingHandler) {
		this.providers = providers;
		this.mappingHandler = mappingHandler;
	}

	protected abstract Hook createHook(Object controller, Method hookCandidate, HttpMethod method);


	public void createAndBindHooks(Object controller) {
		RequestMapping requestMapping = controller.getClass().getAnnotation(RequestMapping.class);
		String prefix = requestMapping != null
				? requestMapping.value()
				: "";

		Method[] hookCandidates = controller.getClass().getDeclaredMethods();
		for (Method hookCandidate : hookCandidates) {

			GetMapping getMapping = hookCandidate.getAnnotation(GetMapping.class);
			if (getMapping != null) {
				Hook hook = createHook(controller, hookCandidate, HttpMethod.GET);
				mappingHandler.addStaticHook(prefix + getMapping.value(), hook);
			}
			PostMapping postMapping = hookCandidate.getDeclaredAnnotation(PostMapping.class);
			if (postMapping != null) {
				Hook hook = createHook(controller, hookCandidate, HttpMethod.POST);
				mappingHandler.addStaticHook(prefix + postMapping.value(), hook);
			}

		}
	}

	public void createAndAllBindHooks(List<Object> controllers) {
		for (Object controller : controllers) {
			createAndBindHooks(controller);
		}
	}

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
