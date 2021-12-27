package ru.fbtw.jacarandaserver.core.context.filters;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;
import ru.fbtw.jacarandaserver.api.serverlet.Filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HttpConfigurationFilter implements Filter {

	private final ServerConfiguration configuration;

	public HttpConfigurationFilter(ServerConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void doFilter(HttpRequest request, HttpResponse.HttpResponseBuilder responseBuilder) {
		// Set server configuration to response
		responseBuilder.setHttpVersion(configuration.getHttpVersion());
		responseBuilder.addHeader(HttpHeader.SERVER.getHeaderName(), configuration.getServerName());

		String httpDate = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss zzz", Locale.ENGLISH)
				.format(new Date());
		responseBuilder.addHeader(HttpHeader.DATE.getHeaderName(), httpDate);
	}
}
