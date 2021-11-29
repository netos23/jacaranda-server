package ru.fbtw.jacarandaserver.core.servlet;

import ru.fbtw.jacarandaserver.api.serverlet.Servlet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServletMappingHandler {

	private final Map<String, Servlet> servletMap;

	public ServletMappingHandler() {
		servletMap = new HashMap<>();
	}

	public void addServlet(String path, Servlet servlet) {
		if (path.endsWith("/")) {
			servletMap.put(path, servlet);
		}
	}

	public void addServlets(Map<String, Servlet> servletMap) {
		for (Map.Entry<String, Servlet> servletEntry : servletMap.entrySet()) {
			addServlet(servletEntry.getKey(), servletEntry.getValue());
		}
	}

	public Servlet getServlet(String path) {
		String servletPath = path;
		if(!servletPath.endsWith("/")){

		}
		return null;
	}
}
