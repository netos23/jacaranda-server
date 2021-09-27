package ru.fbtw.jacarndaserver.requests;

import java.util.Map;

public class HttpResponse {
    private String httpVersion;
    private HttpStatus status;
    private Map<String,String> headers;
    private String body;

    private HttpResponse(String httpVersion, HttpStatus status, Map<String, String> headers, String body) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.headers = headers;
        this.body = body;
    }


}
