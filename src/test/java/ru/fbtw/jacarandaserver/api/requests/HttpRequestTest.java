package ru.fbtw.jacarandaserver.api.requests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.fbtw.jacarandaserver.io.IOUtils;
import ru.fbtw.jacarandaserver.api.requests.exceptions.HttpRequestBuildException;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;
import ru.fbtw.jacarandaserver.test.util.TestReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

class HttpRequestTest {
    private static final String INVALID_TEST_PATH = "requests/invalid_requests/";
    private static final String VALID_TEST_PATH = "requests/valid_requests/test/";
    private static final String VALID_MATCH_DATA = "requests/valid_requests/match_data/";
    private static final ServerConfiguration CONTEXT = ServerConfiguration.DEFAULT_CONFIG;

    @ParameterizedTest
    @MethodSource("loadValidTests")
    void parseValid(final String request, final HttpRequest expected, final String message) {
        try {
            HttpRequest actual = HttpRequest.parse(request, CONTEXT);
            Assertions.assertEquals(expected, actual, message);
        } catch (HttpRequestBuildException e) {
            //todo: log there
            e.printStackTrace();
            Assertions.fail("Unexpected exception during http request build");
        }
    }

    @ParameterizedTest
    @MethodSource("loadInvalidTests")
    void parseInvalid(final String request, final String message) {
        Assertions.assertThrows(HttpRequestBuildException.class, () -> HttpRequest.parse(request, CONTEXT), message);
    }

    private static Stream<Arguments> loadValidTests() {
        File testDir = TestReader.getResFile(VALID_TEST_PATH);
        String[] list = Objects.requireNonNull(testDir.list());

        List<Arguments> res = new ArrayList<>();

        for (String testName : list) {
            File testFile = TestReader.getResFile(VALID_TEST_PATH + testName);
            File matchDataFile = TestReader.getResFile(VALID_MATCH_DATA + testName);
            try {
                String test = IOUtils.readAllStrings(testFile);
                HttpRequest matchData = TestReader.readRequest(matchDataFile, CONTEXT);
                res.add(Arguments.of(test, matchData, testName));
            } catch (IOException | HttpRequestBuildException ex) {
                //todo log there
                ex.printStackTrace();
            }
        }
        return res.stream();
    }

    private static Stream<Arguments> loadInvalidTests() {
        File testDir = TestReader.getResFile(INVALID_TEST_PATH);
        String[] list = Objects.requireNonNull(testDir.list());

        List<Arguments> res = new ArrayList<>();
        for (String testName : list) {
            File testFile = TestReader.getResFile(INVALID_TEST_PATH + testName);
            try {
                String test = IOUtils.readAllStrings(testFile);
                res.add(Arguments.of(test, testName));
            } catch (IOException e) {
                //todo log there
                e.printStackTrace();
            }
        }
        return res.stream();
    }


}