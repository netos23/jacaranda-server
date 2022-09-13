package ru.fbtw.jacarandaserver.test.util;

import ru.fbtw.jacarandaserver.api.requests.HttpRequest;
import ru.fbtw.jacarandaserver.api.requests.Url;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpRequestBuildException;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestReader {
    public static HttpRequest readRequest(File src, ServerConfiguration context)
            throws FileNotFoundException, HttpRequestBuildException {
        Scanner in = new Scanner(src);

        HttpMethod method = HttpMethod.valueOf(in.next());

        Url.UrlBuilder urlBuilder = Url.newBuilder()
                .setHost(context.getHost())
                .setProtocol(context.getProtocol())
                .setContextPath(in.next());

        int queryParamsCount = in.nextInt();
        for (int i = 0; i < queryParamsCount; i++) {
            urlBuilder.addQueryParam(in.next(), in.next());
        }

        String httpVersion = in.next();

        int headersCount = in.nextInt();
        in.nextLine();
        Map<String, String> headers = new HashMap<>();
        for (int i = 0; i < headersCount; i++) {
            headers.put(in.nextLine().trim(), in.nextLine().trim());
        }

        boolean hasBody = in.nextBoolean();
        StringBuilder body = new StringBuilder();
        if (hasBody) {
            in.nextLine();
            while (in.hasNext()) {
                body.append(in.nextLine());
                if (in.hasNext()) {
                    body.append('\n');
                }
            }
        }

        return HttpRequest.newBuilder()
                .setUrl(urlBuilder.build())
                .setHttpVersion(httpVersion)
                .setMethod(method)
                .addHeaders(headers)
                .setBody(hasBody ? body.toString() : null)
                .build();
    }

    public static File getResFile(String filename) {
        ClassLoader classLoader = TestReader.class.getClassLoader();
        URL testResource = classLoader.getResource(filename);

        if (testResource == null) {
            // todo: log there
            throw new IllegalArgumentException("Missing request test dir");
        }

        return new File(testResource.getFile());
    }
}
