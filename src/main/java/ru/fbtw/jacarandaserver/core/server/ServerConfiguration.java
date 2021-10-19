package ru.fbtw.jacarandaserver.core.server;

public class ServerConfiguration {
    public static final ServerConfiguration DEFAULT_CONTEXT =
            new ServerConfiguration(
                    "http", "127.0.0.1", ".",
                    8080, 100, "HTTP/1.1",
                    "classpath:templates/error.html",
                    "classpath:templates/dirinfo.html",
                    "jacaranda server/0.1.0 (Unix) (Mac os)");

    private final String protocol;
    private final String host;
    private final String path;
    private final int port;
    private final int maxConnections;
    private final String httpVersion;
    private final String errorTemplate;
    private final String dirInfoTemplate;
    private final String serverName;

    public ServerConfiguration(
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