package ru.fbtw.jacarandaserver.core.server;

import ru.fbtw.jacarandaserver.configuration.annotations.ConfigName;
import ru.fbtw.jacarandaserver.configuration.annotations.Configuration;
import ru.fbtw.jacarandaserver.configuration.annotations.DefaultConfiguration;

@Configuration(name = "server-config")
public class ServerConfiguration {

	@DefaultConfiguration(target = "server-config")
	public static final ServerConfiguration DEFAULT_CONFIG =
			new ServerConfiguration(
					"http", "127.0.0.1", ".",
					8080, 100, "HTTP/1.1",
					"classpath:templates/error.html",
					"classpath:templates/dirinfo.html",
					"jacaranda server/0.1.0 (Unix) (Mac os)");

	@ConfigName(name = "protocol")
	private String protocol;

	@ConfigName(name = "host")
	private String host;

	@ConfigName(name = "path")
	private String path;

	@ConfigName(name = "port")
	private Integer port;

	@ConfigName(name = "max-connections")
	private Integer maxConnections;

	@ConfigName(name = "http-ver")
	private String httpVersion;

	@ConfigName(name = "error-template")
	private String errorTemplate;

	@ConfigName(name = "dir-info-template")
	private String dirInfoTemplate;

	@ConfigName(name = "server-name")
	private String serverName;

	public ServerConfiguration() {
	}


	private ServerConfiguration(
			String protocol,
			String host,
			String path,
			int port,
			int maxConnections,
			String httpVersion,
			String errorTemplate,
			String dirInfoTemplate,
			String serverName
	) {
		this.protocol = protocol;
		this.host = host;
		this.path = path;
		this.port = port;
		this.maxConnections = maxConnections;
		this.httpVersion = httpVersion;
		this.errorTemplate = errorTemplate;
		this.dirInfoTemplate = dirInfoTemplate;
		this.serverName = serverName;
	}

	public String getErrorTemplate() {
		return errorTemplate;
	}

	public String getDirInfoTemplate() {
		return dirInfoTemplate;
	}

	public String getPath() {
		return path;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public String getServerName() {
		return serverName;
	}
}
