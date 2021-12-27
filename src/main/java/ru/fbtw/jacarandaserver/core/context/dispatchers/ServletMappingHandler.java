package ru.fbtw.jacarandaserver.core.context.dispatchers;

import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.util.pathtree.PathTree;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ServletMappingHandler {
	private static final Predicate<String> PATH_PATTERN = Pattern.compile("").asPredicate();
	public static final String SEPARATOR = "/";
	private final PathTree<Servlet> servletMap;

	private static boolean validatePath(String path) {
		return PATH_PATTERN.test(path);
	}

	private static String toDirectory(String path) {
		if (path.endsWith(SEPARATOR)) {
			return path;
		} else {
			int lastSeparatorIndex = path.lastIndexOf(SEPARATOR.charAt(0));
			if (lastSeparatorIndex >= 0) {
				return path.substring(0, lastSeparatorIndex + 1);
			} else {
				return SEPARATOR;
			}
		}
	}

	public ServletMappingHandler() {
		servletMap = new PathTree<>();
	}

	public void addServlet(String path, Servlet servlet) {
		if (validatePath(path)) {
			String canonicalPath = toDirectory(path);
			servletMap.put(canonicalPath, servlet);
		}
	}

	public void addServlets(Map<String, Servlet> servletMap) {
		for (Map.Entry<String, Servlet> servletEntry : servletMap.entrySet()) {
			addServlet(servletEntry.getKey(), servletEntry.getValue());
		}
	}

	public Optional<Servlet> getServlet(String path) {
		if (validatePath(path)) {
			String canonicalPath = toDirectory(path);
			Servlet servlet = servletMap.get(canonicalPath);
			return Optional.ofNullable(servlet);
		}
		return Optional.empty();
	}


}
