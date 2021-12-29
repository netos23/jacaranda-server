package ru.fbtw.jacarandaserver.api.requests;

import ru.fbtw.util.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Url {
	private String protocol;
	private String login;
	private String password;
	private String host;
	private String port;
	private String contextPath;
	private final Map<String, List<String>> queryParams;
	private String anchor;

	private Url() {
		queryParams = new HashMap<>();
	}

	public Url(String protocol, String authority, String host, String contextPath) {
		this();
		this.protocol = protocol;


		int portSeparator;
		if ((portSeparator = host.indexOf(':')) >= 0) {
			this.host = host.substring(0, portSeparator);
			this.port = host.substring(portSeparator + 1);
		} else {
			this.host = host;
		}

		String pathWithQuery;
		int anchorIndex = -1;
		if ((anchorIndex = contextPath.indexOf('#')) >= 0) {
			pathWithQuery = contextPath.substring(0, anchorIndex);
			anchor = contextPath.substring(anchorIndex + 1);
		} else {
			pathWithQuery = contextPath;
		}

		int querySeparator = -1;
		if ((querySeparator = pathWithQuery.indexOf('?')) >= 0) {

			this.contextPath = pathWithQuery.substring(0, querySeparator);
			String[] queryPairs = pathWithQuery.substring(querySeparator + 1).split("&");

			Map<String, List<String>> queryMap = Arrays.stream(queryPairs)
					.collect(Collectors.toMap(
							str -> str.substring(0, str.indexOf('=')),
							str -> Collections.singletonList(str.substring(str.indexOf('=') + 1).trim()),
							ListUtils::mergeLists
					));

			this.queryParams.putAll(queryMap);
		} else {
			this.contextPath = pathWithQuery;
		}

	}

/*    public static Url parse(final String url){
        UrlBuilder urlBuilder = newBuilder();

        String urlData = url.trim();

        int i = 0;

        // setup protocol
        for(; i < urlData.length(); i++){
            char c = urlData.charAt(i);

            if(c == '/'){
                break;
            }

            if(c == ':'){
                String protocol = urlData.substring(0,i).toLowerCase(Locale.ROOT);
                urlBuilder.setProtocol(protocol);
                break;
            }
        }
    }*/

	public static UrlBuilder newBuilder() {
		return new Url().new UrlBuilder();
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

	public Map<String, List<String>> getQueryParams() {
		return queryParams;
	}

	public String getAnchor() {
		return anchor;
	}


	public class UrlBuilder {
		private UrlBuilder() {

		}

		public UrlBuilder setProtocol(String protocol) {
			Url.this.protocol = protocol;
			return this;
		}

		public UrlBuilder setLogin(String login) {
			Url.this.login = login;
			return this;
		}

		public UrlBuilder setPassword(String password) {
			Url.this.password = password;
			return this;
		}

		public UrlBuilder setHost(String host) {
			Url.this.host = host;
			return this;
		}

		public UrlBuilder setPort(String port) {
			Url.this.port = port;
			return this;
		}

		public UrlBuilder setContextPath(String contextPath) {
			Url.this.contextPath = contextPath;
			return this;
		}

		public UrlBuilder addQueryParams(Map<String, List<String>> queryParams) {
			// todo fix
			queryParams.forEach((k, v) -> v.forEach(q -> addQueryParam(k, q)));
			return this;
		}

		public UrlBuilder addQueryParamsRaw(Map<String, String> queryParams) {
			queryParams.forEach(this::addQueryParam);
			return this;
		}

		public UrlBuilder addQueryParam(String key, String value) {
			List<String> list = queryParams.get(key);
			if (list == null) {
				queryParams.put(key, Collections.singletonList(value));
			} else {
				queryParams.put(key, ListUtils.mergeLists(list, Collections.singletonList(value)));
			}

			return this;
		}

		public UrlBuilder setAnchor(String anchor) {
			Url.this.anchor = anchor;
			return this;
		}

		public Url build() {
			return Url.this;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Url that = (Url) o;
		return protocol.equals(that.protocol)
				&& Objects.equals(login, that.login)
				&& Objects.equals(password, that.password)
				&& host.equals(that.host)
				&& Objects.equals(port, that.port)
				&& contextPath.equals(that.contextPath)
				&& Objects.equals(queryParams, that.queryParams)
				&& Objects.equals(anchor, that.anchor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(protocol, login, password, host, port, contextPath, queryParams, anchor);
	}

	@Override
	public String toString() {
		return "Url{" +
				"protocol='" + protocol + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", host='" + host + '\'' +
				", port='" + port + '\'' +
				", contextPath='" + contextPath + '\'' +
				", queryParams=" + queryParams +
				", anchor='" + anchor + '\'' +
				'}';
	}
}
