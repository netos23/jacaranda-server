package ru.fbtw.jacarandaserver.core.requests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpMethod;

class HttpMethodTest {

    @Test
    void valueOfOrDefault() {
        Assertions.assertEquals(
                HttpMethod.GET,
                HttpMethod.valueOfOrDefault(HttpMethod.GET.name(), HttpMethod.UNSUPPORTED),
                "Valid get test"
        );

        Assertions.assertEquals(
                HttpMethod.GET,
                HttpMethod.valueOfOrDefault("gEt", HttpMethod.UNSUPPORTED),
                "Valid get test with lowercase and uppercase characters"
        );

        Assertions.assertEquals(
                HttpMethod.POST,
                HttpMethod.valueOfOrDefault(HttpMethod.POST.name(), HttpMethod.UNSUPPORTED),
                "Valid post test"
        );

        Assertions.assertEquals(
                HttpMethod.POST,
                HttpMethod.valueOfOrDefault("posT", HttpMethod.UNSUPPORTED),
                "Valid post test with lowercase and uppercase characters"
        );


        Assertions.assertEquals(
                HttpMethod.UNSUPPORTED,
                HttpMethod.valueOfOrDefault(HttpMethod.UNSUPPORTED.name(), HttpMethod.UNSUPPORTED),
                "Valid unsupported test"
        );

        Assertions.assertEquals(
                HttpMethod.UNSUPPORTED,
                HttpMethod.valueOfOrDefault("put", HttpMethod.UNSUPPORTED),
                "Valid unsupported test with missing value"
        );

        Assertions.assertEquals(
                HttpMethod.UNSUPPORTED,
                HttpMethod.valueOfOrDefault("Del it", HttpMethod.UNSUPPORTED),
                "Valid unsupported test with space"
        );

        Assertions.assertEquals(
                HttpMethod.UNSUPPORTED,
                HttpMethod.valueOfOrDefault("", HttpMethod.UNSUPPORTED),
                "Valid unsupported test with empty string"
        );

        Assertions.assertEquals(
                HttpMethod.UNSUPPORTED,
                HttpMethod.valueOfOrDefault(null, HttpMethod.UNSUPPORTED),
                "Valid unsupported test with null"
        );
    }
}