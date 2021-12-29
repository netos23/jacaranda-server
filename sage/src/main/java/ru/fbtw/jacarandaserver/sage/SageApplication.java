package ru.fbtw.jacarandaserver.sage;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Controller;
import ru.fbtw.jacarandaserver.sage.bean.internal.BeanRegistry;
import ru.fbtw.jacarandaserver.sage.bean.internal.BeanRegistryLoader;
import ru.fbtw.jacarandaserver.sage.bean.internal.exception.NoSuchBeanException;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;
import ru.fbtw.jacarandaserver.sage.controller.hook.Hook;
import ru.fbtw.jacarandaserver.sage.controller.hook.MvcHook;
import ru.fbtw.jacarandaserver.sage.controller.mapping.GetMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.PostMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMapping;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProvider;
import ru.fbtw.jacarandaserver.sage.controller.request.providers.RequestProviders;
import ru.fbtw.jacarandaserver.sage.view.DataModelWithView;
import ru.fbtw.jacarandaserver.sage.view.MvcViewPresenter;
import ru.fbtw.jacarandaserver.sage.view.ViewPresenter;
import ru.fbtw.jacarandaserver.sage.view.resolvers.ContentTypeResolver;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SageApplication {

	public static SageApplication createSageApp(List<ContentTypeResolver> resolvers) {
		BeanRegistry registry = new BeanRegistryLoader().loadBeans();

		WebMvcConfiguration webMvcConfiguration = null;
		try {
			webMvcConfiguration = registry.getAnyBeanByClass(WebMvcConfiguration.class);
		} catch (NoSuchBeanException e) {
			//todo log there
			webMvcConfiguration = new WebMvcConfiguration();
		}
		try {
			webMvcConfiguration.configure();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Object> controllers;
		RequestProviders providers = null;
		MvcViewPresenter mvcViewPresenter = null;
		try {
			controllers = registry.getAllBeansByAnnotatedType(Controller.class);
			providers = registry.getAnyBeanByClass(RequestProviders.class);
			mvcViewPresenter = registry.getAnyBeanByClass(MvcViewPresenter.class);
		} catch (NoSuchBeanException e) {
			controllers = Collections.emptyList();
		}

		buildControllers(controllers, providers, mvcViewPresenter);

		return null;
	}

	private static void buildControllers(
			List<Object> controllers,
			RequestProviders providers,
			ViewPresenter<?> viewPresenter
	) {
		for (Object controller : controllers) {
			RequestMapping requestMapping = controller.getClass().getDeclaredAnnotation(RequestMapping.class);

			Method[] hookCandidates = controller.getClass().getDeclaredMethods();
			for (Method hookCandidate : hookCandidates) {
				Hook hook;

				GetMapping getMapping = hookCandidate.getAnnotation(GetMapping.class);
				if (getMapping != null) {
					hook = buildMvcHook(
							providers,
							(ViewPresenter<DataModelWithView>) viewPresenter,
							controller,
							hookCandidate,
							HttpMethod.GET
					);

				}
				PostMapping postMapping = hookCandidate.getDeclaredAnnotation(PostMapping.class);
				if (postMapping != null) {
					hook = buildMvcHook(
							providers,
							(ViewPresenter<DataModelWithView>) viewPresenter,
							controller,
							hookCandidate,
							HttpMethod.POST
					);
				}
			}
		}
	}

	private static Hook buildMvcHook(
			RequestProviders providers,
			ViewPresenter<DataModelWithView> viewPresenter,
			Object controller,
			Method hookCandidate,
			HttpMethod method
	) {
		RequestProvider[] requestProviders = getRequestProviders(providers, hookCandidate);

		return new MvcHook(
				method,
				requestProviders,
				methodToCallback(hookCandidate, controller),
				viewPresenter
		);
	}

	private static RequestProvider[] getRequestProviders(RequestProviders providers, Method hookCandidate) {
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

	private static Function<Object[], Object> methodToCallback(Method method, Object controller) {
		return args -> {
			try {
				method.setAccessible(true);
				return method.invoke(controller, args);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException("Can`t assemble hook", e);
			}
		};
	}

	public void handle(HttpRequest request, HttpResponse.HttpResponseBuilder httpResponseBuilder) {

	}

}
