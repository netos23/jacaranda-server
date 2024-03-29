package ru.fbtw.jacarandaserver.core.context;


import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpResponseBuildException;
import ru.fbtw.jacarandaserver.api.serverlet.ExceptionHandler;
import ru.fbtw.jacarandaserver.api.serverlet.Filter;
import ru.fbtw.jacarandaserver.api.serverlet.Resolver;
import ru.fbtw.jacarandaserver.api.serverlet.Servlet;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;
import ru.fbtw.jacarandaserver.core.context.dispatchers.DispatcherServlet;
import ru.fbtw.jacarandaserver.core.context.dispatchers.ServletMappingHandler;
import ru.fbtw.jacarandaserver.core.context.filters.HostFilter;
import ru.fbtw.jacarandaserver.core.context.filters.HttpConfigurationFilter;
import ru.fbtw.jacarandaserver.core.context.resolvers.KeepAliveResolver;
import ru.fbtw.jacarandaserver.sage.app.ServletFacade;

public class ServletContext {
	private final ServerConfiguration configuration;
	private final ServletMappingHandler mappingHandler;
	private final Servlet dispatcherServlet;
	private final Filter preRequestFilterChain;
	private final Filter afterRequestFilterChain;
	private final ExceptionHandler exceptionHandler;
	private final Resolver<Boolean> keepAliveResolver;

	public static ServletContext createContext(ServerConfiguration configuration) {

		Servlet internalServlet = new ServletFacade();
		ServletMappingHandler mappingHandler = new ServletMappingHandler();
		Servlet dispatcherServlet = new DispatcherServlet(internalServlet, mappingHandler);

		Filter hostFilter = new HostFilter();
		Filter configurationFilter = new HttpConfigurationFilter(configuration);
		ExceptionHandler exceptionHandler = exception -> {
			try {
				return HttpResponse.newBuilder().build();
			} catch (HttpResponseBuildException e) {
				e.printStackTrace();
			}
			return null;
		};
		Resolver<Boolean> resolver = new KeepAliveResolver("Close", true);
		return new ServletContext(
				configuration,
				mappingHandler,
				dispatcherServlet,
				hostFilter,
				configurationFilter,
				exceptionHandler,
				resolver
		);
	}

	private ServletContext(
			ServerConfiguration configuration,
			ServletMappingHandler mappingHandler, Servlet dispatcherServlet,
			Filter preRequestFilterChain,
			Filter afterRequestFilterChain,
			ExceptionHandler exceptionHandler,
			Resolver<Boolean> keepAliveResolver
	) {
		this.configuration = configuration;
		this.mappingHandler = mappingHandler;
		this.dispatcherServlet = dispatcherServlet;
		this.preRequestFilterChain = preRequestFilterChain;
		this.afterRequestFilterChain = afterRequestFilterChain;
		this.exceptionHandler = exceptionHandler;
		this.keepAliveResolver = keepAliveResolver;
	}

	public ServerConfiguration getConfiguration() {
		return configuration;
	}

	public Filter getPreRequestFilterChain() {
		return preRequestFilterChain;
	}

	public Filter getAfterRequestFilterChain() {
		return afterRequestFilterChain;
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public Servlet getDispatcherServlet() {
		return dispatcherServlet;
	}

	public Resolver<Boolean> getKeepAliveResolver() {
		return keepAliveResolver;
	}

	public ServletMappingHandler getMappingHandler() {
		return mappingHandler;
	}
}
