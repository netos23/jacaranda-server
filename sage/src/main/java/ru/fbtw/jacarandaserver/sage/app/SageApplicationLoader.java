package ru.fbtw.jacarandaserver.sage.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.sage.app.exception.SageApplicationCreationException;
import ru.fbtw.jacarandaserver.sage.app.annotation.Controller;
import ru.fbtw.jacarandaserver.sage.app.annotation.RestController;
import ru.fbtw.jacarandaserver.sage.bean.internal.BeanRegistry;
import ru.fbtw.jacarandaserver.sage.bean.internal.BeanRegistryLoader;
import ru.fbtw.jacarandaserver.sage.bean.internal.exception.NoSuchBeanException;
import ru.fbtw.jacarandaserver.sage.configuration.WebMvcConfiguration;
import ru.fbtw.jacarandaserver.sage.configuration.exception.ConfigurationCreationException;
import ru.fbtw.jacarandaserver.sage.controller.filter.AfterRequestFilterChain;
import ru.fbtw.jacarandaserver.sage.controller.filter.PreRequestFilterChain;
import ru.fbtw.jacarandaserver.sage.controller.hook.AbstractHookFactory;
import ru.fbtw.jacarandaserver.sage.controller.hook.Hook;
import ru.fbtw.jacarandaserver.sage.controller.hook.ResourceHandlerHook;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMappingHandler;
import ru.fbtw.jacarandaserver.sage.exception.GenericExceptionHandlerRegistry;
import ru.fbtw.jacarandaserver.sage.view.FilePresenter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class SageApplicationLoader {
	private SageApplicationLoader() {
	}

	private static final Logger logger = LoggerFactory.getLogger(SageApplicationLoader.class);

	public static SageApplication createSageApp() {
		logger.info("Start sage application assemble");
		logger.debug("Initialize sage app beans");
		BeanRegistry registry = new BeanRegistryLoader().loadBeans();

		WebMvcConfiguration webMvcConfiguration = buildWebMvcConfiguration(registry);

		buildMvcControllers(registry);
		buildRestControllers(registry);

		RequestMappingHandler mappingHandler = registry.getAnyNullableBeanByClass(RequestMappingHandler.class);
		buildResourceHandlers(registry, webMvcConfiguration, mappingHandler);

		GenericExceptionHandlerRegistry exceptionHandlerRegistry = buildExceptionHandlerRegistry(registry);
		PreRequestFilterChain preRequestFilterChain = buildPreRequestFilterChain(registry);
		AfterRequestFilterChain afterRequestFilterChain = buildAfterRequestFilterChain(registry);

		return SageApplication.builder()
				.setMvcConfig(webMvcConfiguration)
				.setRequestMappingHandler(mappingHandler)
				.setExceptionHandlerRegistry(exceptionHandlerRegistry)
				.setPreRequestFilterChain(preRequestFilterChain)
				.setAfterRequestFilterChain(afterRequestFilterChain)
				.build();
	}

	private static AfterRequestFilterChain buildAfterRequestFilterChain(BeanRegistry registry) {
		AfterRequestFilterChain afterRequestFilterChain = null;
		try {
			afterRequestFilterChain = registry.getAnyBeanByClass(AfterRequestFilterChain.class);
		} catch (NoSuchBeanException e) {
			logger.error("Can`t load after request filter chain. Using no-op filter chain");
			afterRequestFilterChain = new AfterRequestFilterChain(Collections.emptyList());
		}
		return afterRequestFilterChain;
	}

	private static PreRequestFilterChain buildPreRequestFilterChain(BeanRegistry registry) {
		PreRequestFilterChain preRequestFilterChain = null;
		try {
			preRequestFilterChain = registry.getAnyBeanByClass(PreRequestFilterChain.class);
		} catch (NoSuchBeanException e) {
			logger.error("Can`t load pre request filter chain. Using no-op filter chain");
			preRequestFilterChain = new PreRequestFilterChain(Collections.emptyList());
		}
		return preRequestFilterChain;
	}

	private static GenericExceptionHandlerRegistry buildExceptionHandlerRegistry(BeanRegistry registry) {
		GenericExceptionHandlerRegistry exceptionHandlerRegistry = null;
		try {
			exceptionHandlerRegistry = registry
					.getAnyBeanByClass(GenericExceptionHandlerRegistry.class);
		} catch (NoSuchBeanException e) {
			logger.error("Can`t load exception handler registry");
			throw new SageApplicationCreationException("Required bean not found", e);
		}
		return exceptionHandlerRegistry;
	}

	private static void buildResourceHandlers(
			BeanRegistry registry,
			WebMvcConfiguration webMvcConfiguration,
			RequestMappingHandler mappingHandler
	) {
		FilePresenter filePresenter;
		try {
			filePresenter = registry.getAnyBeanByClass(FilePresenter.class);
		} catch (NoSuchBeanException e) {
			logger.warn("Can`t find FilePresenter bean. Using default implementation");
			filePresenter = new FilePresenter();
		}

		for (Map.Entry<String, String> resourceHandlerEntry
				: webMvcConfiguration.getResourceHandlerRegistry().entrySet()) {
			String rootPath = webMvcConfiguration.getRootPath();
			Hook hook = new ResourceHandlerHook(
					filePresenter,
					resourceHandlerEntry.getKey(),
					rootPath.substring(0, rootPath.length() - 1) + resourceHandlerEntry.getValue()
			);
			mappingHandler.addDynamicHook(resourceHandlerEntry.getValue(), hook);
		}
	}

	private static void buildRestControllers(BeanRegistry registry) {
		List<Object> controllers;
		try {
			controllers = registry.getAllBeansByAnnotatedType(RestController.class);
		} catch (NoSuchBeanException e) {
			logger.warn("Zero Rest controllers detected or beans missing");
			controllers = Collections.emptyList();
		}

		for (Object controller : controllers) {
			RestController controllerAnnotation = controller.getClass().getDeclaredAnnotation(RestController.class);
			try {
				AbstractHookFactory controllerHookFactory =
						registry.getAnyBeanByClass(controllerAnnotation.hookFactory());
				controllerHookFactory.createAndBindHooks(controller);
			} catch (NoSuchBeanException e) {
				logger.warn("Can`t find factory for Rest controller {}. Ignoring controller", controller);
				e.printStackTrace();
			}
		}
	}

	private static void buildMvcControllers(BeanRegistry registry) {
		List<Object> controllers;
		try {
			controllers = registry.getAllBeansByAnnotatedType(Controller.class);
		} catch (NoSuchBeanException e) {
			logger.warn("Zero MVC controllers detected or beans missing");
			controllers = Collections.emptyList();
		}

		for (Object controller : controllers) {
			Controller controllerAnnotation = controller.getClass().getDeclaredAnnotation(Controller.class);
			try {
				AbstractHookFactory controllerHookFactory =
						registry.getAnyBeanByClass(controllerAnnotation.hookFactory());
				controllerHookFactory.createAndBindHooks(controller);
			} catch (NoSuchBeanException e) {
				logger.warn("Can`t find factory for MVC controller {}. Ignoring controller", controller);
				e.printStackTrace();
			}
		}
	}

	private static WebMvcConfiguration buildWebMvcConfiguration(BeanRegistry registry) {
		WebMvcConfiguration webMvcConfiguration = registry.getAnyNullableBeanByClass(WebMvcConfiguration.class);

		try {
			webMvcConfiguration.configure();
		} catch (IOException e) {
			logger.error("Can`t configure sage app because IO exception occurred.");
			throw new ConfigurationCreationException("Can`t configure sage configuration", e);
		}

		return webMvcConfiguration;
	}

}
