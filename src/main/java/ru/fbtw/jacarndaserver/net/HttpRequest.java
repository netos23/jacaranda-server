package ru.fbtw.jacarndaserver.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
	private Url url;
	private HttpMethod method;
	private String httpVersion;
	private Map<String, String> headers;
	private String body;

	private HttpRequest() {
		headers = new HashMap<>();
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
		return headers.get(header);
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
