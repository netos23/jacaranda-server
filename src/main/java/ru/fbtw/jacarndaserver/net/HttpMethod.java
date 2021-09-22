package ru.fbtw.jacarndaserver.net;

public enum HttpMethod {
	GET,
	UNSUPPORTED;

	public HttpMethod valueOfOrDefault(String value, HttpMethod defaultVal) {
		HttpMethod method;
		try {
			method = HttpMethod.valueOf(value);
		} catch (IllegalArgumentException ex) {
			method = defaultVal;
		}
		return method;
	}
}
