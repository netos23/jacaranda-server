package ru.fbtw.jacarandaserver.core.bootloader;

import ru.fbtw.jacarandaserver.api.annotations.WebApplication;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.core.context.dispatchers.ServletMappingHandler;
import ru.fbtw.util.packegescanner.SimplePackageScanner;
import ru.fbtw.util.reflect.Reflections;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletBootloader {

	private final ServletMappingHandler handler;
	private final List<String> packages;

	public ServletBootloader(ServletMappingHandler handler, List<String> packages) {
		this.handler = handler;
		this.packages = packages;
	}

	public Map<String, Servlet> load(String servletFilename) {
		File jar = new File(servletFilename);
		try {
			URLClassLoader classLoader = new URLClassLoader(
					new URL[]{jar.toURI().toURL()},
					this.getClass().getClassLoader()
			);

			SimplePackageScanner scanner = new SimplePackageScanner(classLoader);
			scanner.scanPackages(packages);

			List<Class<?>> markedClasses = scanner.getMarkedClasses(WebApplication.class);
			return getServletMap(markedClasses);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return Collections.emptyMap();
	}

	private Map<String, Servlet> getServletMap(List<Class<?>> markedClasses) {
		Map<String, Servlet> servletMap = new HashMap<>();
		for (Class<?> clazz : markedClasses) {
			if (Reflections.hasInterface(clazz, Servlet.class)) {
				try {
					Constructor<?> constructor = clazz.getConstructor();
					Servlet servlet = (Servlet) constructor.newInstance();
					WebApplication webApplication = clazz.getAnnotation(WebApplication.class);
					servletMap.put(webApplication.path(),servlet);
				} catch (NoSuchMethodException
						| InvocationTargetException
						| InstantiationException
						| IllegalAccessException e
				) {
					e.printStackTrace();
				}
			}
		}
		return servletMap;
	}
}
