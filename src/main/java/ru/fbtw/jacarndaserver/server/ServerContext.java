package ru.fbtw.jacarndaserver.server;

public class ServerContext {
    private final String protocol;
    private final String host;
    private final String path;

    public ServerContext(String protocol, String host, String path) {
        this.protocol = protocol;
        this.host = host;
        this.path = path;
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
}
