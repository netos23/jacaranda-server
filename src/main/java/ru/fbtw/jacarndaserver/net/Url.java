package ru.fbtw.jacarndaserver.net;

import java.util.HashMap;
import java.util.Map;

public class Url {
	private String protocol;
	private String login;
	private String password;
	private String host;
	private String port;
	private String contextPath;
	private Map<String, String> queryParams;
	private String anchor;

	private Url() {
		queryParams = new HashMap<>();
	}

	public String getProtocol() {
		return protocol;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public String getAnchor() {
		return anchor;
	}


}
