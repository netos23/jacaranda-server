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
	public static final String SUFFIX = ".jar";

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
			getSnapshot();
			try {
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void getSnapshot() {
		File file = new File(path);
		String[] list = file.list();
		if (list != null) {
			List<String> currentSnapshot = Arrays.stream(list)
					.filter(f -> f.endsWith(SUFFIX))
					.collect(Collectors.toList());

			for (String servletJar : currentSnapshot) {
				if (!snapshot.contains(servletJar)) {
					logger.debug("New servlet detected: {}", servletJar);

					String path = file.getAbsolutePath() + servletJar;
					Map<String, Servlet> servletMap = bootloader.load(path);
					if (!servletMap.isEmpty()) {
						snapshot.add(servletJar);
						for (String servletPath : servletMap.keySet()) {
							logger.debug("New servlet loaded on: {}", servletPath);
						}
					}
				}
			}
		}
	}
}
