package ru.fbtw.jacarandaserver.core.bootloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class ServletBootstrap implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ServletBootstrap.class);
	public static final String JAR_SUFFIX = ".jar";

	private final ServletBootloader bootloader;
	private final Set<String> snapshot;
	private final String path;
	private final long delay;


	public ServletBootstrap(ServletBootloader bootloader, String path, long delay) {
		this.bootloader = bootloader;
		this.path = path;
		this.delay = delay;
		snapshot = new HashSet<>();
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			logger.debug("Get snapshot");
			listenChanges();
			try {
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void listenChanges() {
		File file = new File(path);
		String[] list = file.list();

		List<String> currentSnapshot = getSnapshot(list);
		for (String servletJar : currentSnapshot) {
			if (!snapshot.contains(servletJar)) {
				applyChanges(file.getAbsolutePath(), servletJar);
			}
		}

	}

	private void applyChanges(String path, String filename) {
		logger.debug("New servlet detected: {}", filename);

		String absolutePath = path + filename;
		Map<String, Servlet> servletMap = bootloader.load(absolutePath);
		if (!servletMap.isEmpty()) {
			snapshot.add(filename);
			for (String servletPath : servletMap.keySet()) {
				logger.debug("New servlet loaded on: {}", servletPath);
			}
		}
	}

	private List<String> getSnapshot(String[] list) {
		if(list == null){
			return Collections.emptyList();
		}

		return Arrays.stream(list)
				.filter(f -> f.endsWith(JAR_SUFFIX))
				.collect(Collectors.toList());
	}
}
