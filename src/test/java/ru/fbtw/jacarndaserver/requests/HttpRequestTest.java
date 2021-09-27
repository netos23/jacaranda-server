package ru.fbtw.jacarndaserver.requests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.fbtw.jacarndaserver.server.ServerContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class HttpRequestTest {

    private final ServerContext context = new ServerContext("http", "host", "path");
    private final String requestPath = "request.txt";
    private  String request;


    @BeforeEach
    void setUp() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(requestPath).getFile());
        Scanner in = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (in.hasNext()){
            builder.append(in.nextLine())
                    .append('\n');
        }
        request = builder.toString();
    }

    @org.junit.jupiter.api.Test
    void parse() {
        try {
            HttpRequest.parse(request, context);
        } catch (HttpRequestBuildException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }
}