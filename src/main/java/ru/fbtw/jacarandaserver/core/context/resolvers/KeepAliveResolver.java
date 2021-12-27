package ru.fbtw.jacarandaserver.core.context.resolvers;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.api.serverlet.Resolver;

public class KeepAliveResolver implements Resolver<Boolean> {
	private final String serverConnectionPolicy;
	private final boolean forceServerConnectionPolicy;

	public KeepAliveResolver(String serverConnectionPolicy, boolean forceServerConnectionPolicy) {
		this.serverConnectionPolicy = serverConnectionPolicy;
		this.forceServerConnectionPolicy = forceServerConnectionPolicy;
	}

	@Override
	public Boolean resolve(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		String clientConnectionPolicy = request.getHeader(HttpHeader.CONNECTION.getHeaderName());

		String resultConnectionPolicy = serverConnectionPolicy;

		if (clientConnectionPolicy != null && !forceServerConnectionPolicy) {
			resultConnectionPolicy = clientConnectionPolicy;
		}

		responseBuilder.addHeader(HttpHeader.CONNECTION.getHeaderName(), resultConnectionPolicy);

		return resultConnectionPolicy.equals("Keep-alive");
	}
}
