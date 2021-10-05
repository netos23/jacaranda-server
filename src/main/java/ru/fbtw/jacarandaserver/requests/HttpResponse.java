package ru.fbtw.jacarandaserver.requests;

import ru.fbtw.jacarandaserver.requests.enums.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String httpVersion;
    private HttpStatus status;
    private final Map<String, String> headers;
    private byte[] body;

    private HttpResponse() {
        headers = new HashMap<>();
    }

    private HttpResponse(String httpVersion, HttpStatus status, Map<String, String> headers, byte[] body) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.headers = headers;
        this.body = body;
    }


    public static HttpResponseBuilder newBuilder() {
        return new HttpResponse().new HttpResponseBuilder();
    }


    public void write(OutputStream out) throws IOException {
        PrintWriter writer = new PrintWriter(out);

        // write status line
        writer.printf("%s ", httpVersion);
        writer.printf("%s ", status.getCode());
        writer.println(status.name());

        // write headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writer.printf("%s: %s\n", header.getKey(), header.getValue());
        }
        // write empty line as body separator
        writer.println();
        // write response status and headers
        writer.flush();

        if (body != null) {
            out.write(body);
            out.flush();
        }
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public byte[] getBody() {
        return body;
    }

    public class HttpResponseBuilder {

        private HttpResponseBuilder() {
        }

        public String getHttpVersion() {
            return httpVersion;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getHeader(String header) {
            return headers.get(header);
        }

        public byte[] getBody() {
            return body;
        }

        public HttpResponseBuilder setHttpVersion(String httpVersion) {
            HttpResponse.this.httpVersion = httpVersion;
            return this;
        }

        public HttpResponseBuilder setBody(byte[] body) {
            HttpResponse.this.body = body;
            return this;
        }

        public HttpResponseBuilder addHeader(String header, String value) {
            HttpResponse.this.headers.put(header, value);
            return this;
        }

        public HttpResponseBuilder setStatus(HttpStatus status) {
            HttpResponse.this.status = status;
            return this;
        }

        public HttpResponse build() {
            return HttpResponse.this;
        }


    }
}
