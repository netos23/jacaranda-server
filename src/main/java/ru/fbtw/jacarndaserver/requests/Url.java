package ru.fbtw.jacarndaserver.requests;

import java.util.HashMap;
import java.util.Map;

public class Url {
    private String protocol;
    private String login;
    private String password;
    private String host;
    private String port;
    private String contextPath;
    private final Map<String, String> queryParams;
    private String anchor;

    private Url() {
        queryParams = new HashMap<>();
    }

    public Url(String protocol, String host, String contextPath) {
        this();

    }

/*    public static Url parse(final String url){
        UrlBuilder urlBuilder = newBuilder();

        String urlData = url.trim();

        int i = 0;

        // setup protocol
        for(; i < urlData.length(); i++){
            char c = urlData.charAt(i);

            if(c == '/'){
                break;
            }

            if(c == ':'){
                String protocol = urlData.substring(0,i).toLowerCase(Locale.ROOT);
                urlBuilder.setProtocol(protocol);
                break;
            }
        }
    }*/

    public static UrlBuilder newBuilder() {
        return new Url().new UrlBuilder();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getAnchor() {
        return anchor;
    }


    public class UrlBuilder {
        private UrlBuilder() {

        }

        public UrlBuilder setProtocol(String protocol) {
            Url.this.protocol = protocol;
            return this;
        }

        public UrlBuilder setLogin(String login) {
            Url.this.login = login;
            return this;
        }

        public UrlBuilder setPassword(String password) {
            Url.this.password = password;
            return this;
        }

        public UrlBuilder setHost(String host) {
            Url.this.host = host;
            return this;
        }

        public UrlBuilder setPort(String port) {
            Url.this.port = port;
            return this;
        }

        public UrlBuilder setContextPath(String contextPath) {
            Url.this.contextPath = contextPath;
            return this;
        }

        public UrlBuilder setQueryParams(Map<String, String> queryParams) {
            Url.this.queryParams
                    .putAll(queryParams);

            return this;
        }

        public UrlBuilder setQueryParams(String key, String value) {
            Url.this.queryParams
                    .put(key, value);

            return this;
        }

        public UrlBuilder setAnchor(String anchor) {
            Url.this.anchor = anchor;
            return this;
        }

        public Url build(){
            return Url.this;
        }
    }


}
