package com.project.sa.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationParserTest {
    public static Stream<Arguments> data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("limit", "5");
        parameters1.put("offset", "4");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("limit", "3");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("limit", "14");
        parameters3.put("offset", "10");
        return Stream.of(
                Arguments.of(parameters1, new Pagination(5, 4)),
                Arguments.of(parameters2, new Pagination(3, 0)),
                Arguments.of(parameters3, new Pagination(14, 10))
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void parsePaginationTest(Map<String, String> parameters, Pagination expected) {
        Pagination actual = PaginationParser.parsePagination(parameters);

        assertEquals(expected, actual);
    }
}