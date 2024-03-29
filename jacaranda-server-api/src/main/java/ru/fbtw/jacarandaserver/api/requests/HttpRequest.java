package ru.fbtw.jacarandaserver.api.requests;

import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpRequestBuildException;
import ru.fbtw.util.io.InputReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
	private Url url;
	private HttpMethod method;
	private String httpVersion;
	private final Map<String, String> headers;
	private String body;

	private HttpRequest() {
		headers = new HashMap<>();
	}

	public static HttpRequest parse(InputStream is, String host, String protocol)
			throws HttpRequestBuildException, IOException {
		return parse(new InputReader(is), host, protocol);
	}

	public static HttpRequest parse(String request, String host, String protocol)
			throws HttpRequestBuildException, IOException {
		return parse(new InputReader(request), host, protocol);
	}

	private static HttpRequest parse(InputReader sc, String host, String protocol)
			throws HttpRequestBuildException, IOException {
		try {
			// read method
			HttpMethod method = HttpMethod.valueOfOrDefault(sc.next(), HttpMethod.UNSUPPORTED);
			// read cp
			String contextPath = sc.next();
			// parse url
			Url url = new Url(protocol, null, host, contextPath);
			// read http version
			String httpVer = sc.next();
			// parse headers
			String rawHeader;
			Map<String, String> headers = new HashMap<>();
			while ((rawHeader = sc.nextLine()) != null && !rawHeader.isEmpty()) {
				int delimiterIndex = rawHeader.indexOf(':');
				String headerName = rawHeader.substring(0, delimiterIndex);
				String headerValue = rawHeader.substring(delimiterIndex + 1).trim();
				headers.put(headerName, headerValue);
			}
			// parse body
			String body = null;
			if (headers.containsKey(HttpHeader.CONTENT_LENGTH.getHeaderName())) {
				char[] buffer = new char[Integer.parseInt(headers.get(HttpHeader.CONTENT_LENGTH.getHeaderName()))];
				sc.readBuffer(buffer);
				body = new String(buffer);
			}

			return newBuilder()
					.setMethod(method)
					.setUrl(url)
					.setHttpVersion(httpVer)
					.addHeaders(headers)
					.setBody(body)
					.build();

		} catch (IOException | HttpRequestBuildException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new HttpRequestBuildException("An unknown Error occurred during request parsing", ex);
		}
	}

	public static HttpRequestBuilder newBuilder() {
		return new HttpRequest().new HttpRequestBuilder();
	}

	public Url getUrl() {
		return url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}

	public String getHeader(String header) {
		return headers.getOrDefault(header, null);
	}


	public class HttpRequestBuilder {

		private HttpRequestBuilder() {
		}

		public HttpRequestBuilder setUrl(Url url) {
			HttpRequest.this.url = url;
			return this;
		}

		public HttpRequestBuilder setMethod(HttpMethod method) {
			HttpRequest.this.method = method;
			return this;
		}

		public HttpRequestBuilder setHttpVersion(String httpVersion) {
			HttpRequest.this.httpVersion = httpVersion;
			return this;
		}

		public HttpRequestBuilder addHeaders(Map<String, String> headers) {
			HttpRequest.this
					.headers
					.putAll(headers);

			return this;
		}

		public HttpRequestBuilder addHeader(String header, String val) {
			HttpRequest.this
					.headers
					.put(header, val);

			return this;
		}

		public HttpRequestBuilder setBody(String body) {
			HttpRequest.this.body = body;
			return this;
		}

		public HttpRequest build() throws HttpRequestBuildException {
			validateHttpRequest();

			return HttpRequest.this;
		}

		private void validateHttpRequest() throws HttpRequestBuildException {
			if (HttpRequest.this.url == null
					|| HttpRequest.this.httpVersion == null
					|| HttpRequest.this.method == null) {

				String message = String.format("One or more required fields null: " +
						"Url: %s, HTTP version: %s, HTTP method: %s", url, httpVersion, method);

				throw new HttpRequestBuildException(message);
			}

			if (!httpVersion
					.toUpperCase(Locale.ROOT)
					.matches("HTTP/\\d\\.\\d")) {

				String message = String.format("%s - wrong http version", httpVersion);

				throw new HttpRequestBuildException(message);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HttpRequest that = (HttpRequest) o;
		return Objects.equals(url, that.url)
				&& method == that.method
				&& Objects.equals(httpVersion, that.httpVersion)
				&& Objects.equals(headers, that.headers)
				&& Objects.equals(body, that.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, method, httpVersion, headers, body);
	}


	@Override
	public String toString() {
		return "HttpRequest{" +
				"url=" + url +
				", method=" + method +
				", httpVersion='" + httpVersion + '\'' +
				", headers=" + headers +
				", body='" + body + '\'' +
				'}';
	}
}
