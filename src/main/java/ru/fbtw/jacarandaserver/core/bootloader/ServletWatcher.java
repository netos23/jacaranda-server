package ru.fbtw.jacarandaserver.core.bootloader;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.annotations.WebApplication;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.core.context.dispatchers.ServletMappingHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.Set;

public class ServletWatcher implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ServletWatcher.class);

	private final String servletPath;
	private final ServletMappingHandler servletMappingHandler;

	public ServletWatcher(
			String servletPath,
			ServletMappingHandler servletMappingHandler
	) {
		this.servletPath = servletPath;
		this.servletMappingHandler = servletMappingHandler;
	}

	@Override
	public void run() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Path path = Paths.get(servletPath);
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			while (!Thread.currentThread().isInterrupted()) {
				WatchKey key;
				while ((key = watchService.take()) != null) {
					for (WatchEvent<?> event : key.pollEvents()) {
						if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
							logger.info("New servlet candidate added");
							Path context = (Path) event.context();
							File servletFile = context.toFile();
							loadServlets(servletFile);
						}
					}
					key.reset();
				}
			}
		} catch (IOException e) {
			logger.error("Servlet location isn`t exist");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void loadServlets(File servletFile) throws MalformedURLException {
		URLClassLoader classLoader = new URLClassLoader(
				new URL[]{servletFile.toURI().toURL()},
				this.getClass().getClassLoader()
		);

		Configuration scannerConfiguration = new ConfigurationBuilder()
				.addClassLoaders(classLoader)
				.forPackages(".");

		Reflections scanner = new Reflections(scannerConfiguration);
		Set<Class<?>> webApps = scanner.getTypesAnnotatedWith(WebApplication.class);

		for (Class<?> clazz : webApps) {
			Constructor<?>[] constructors = clazz.getConstructors();
			if (constructors.length != 1) {
				logger.error("Can`t load servlet: Wrong constructor constructor count");
				continue;
			}

			try {
				Servlet servlet = (Servlet) constructors[0].newInstance();
				servletMappingHandler.addServlet(servlet.getRootPath(), servlet);
			} catch (InstantiationException |
					InvocationTargetException |
					IllegalAccessException e
			) {
				logger.error("Can`t load servlet: Wrong constructor signature. " +
						"Servlet constructor must have zero parameters");
				e.printStackTrace();

			} catch (ClassCastException e) {
				logger.error("Can`t load servlet: Servlet isn`t instance of Servlet class");
				e.printStackTrace();
			}

		}
	}
}
