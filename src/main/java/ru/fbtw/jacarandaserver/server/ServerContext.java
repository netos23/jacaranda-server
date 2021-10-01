package ru.fbtw.jacarandaserver.server;

public class ServerContext {
    public static final ServerContext DEFAULT_CONTEXT =
            new ServerContext(
                    "http", "127.0.0.1", ".",
                    8080, 100, "HTTP/1.1", "", "");

    private final String protocol;
    private final String host;
    private final String path;
    private final int socket;
    private final int maxConnections;
    private final String httpVersion;
    private final String errorTemplate;
    private final String dirInfoTemplate;

    public ServerContext(
            String protocol,
            String host,
            String path,
            int socket,
            int maxConnections,
            String httpVersion,
            String errorTemplate,
            String dirInfoTemplate) {
        this.protocol = protocol;
        this.host = host;
        this.path = path;
        this.socket = socket;
        this.maxConnections = maxConnections;
        this.httpVersion = httpVersion;
        this.errorTemplate = errorTemplate;
        this.dirInfoTemplate = dirInfoTemplate;
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

    public int getSocket() {
        return socket;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
