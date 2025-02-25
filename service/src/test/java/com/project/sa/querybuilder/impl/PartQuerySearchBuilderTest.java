package com.project.sa.querybuilder.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartQuerySearchBuilderTest {
    PartQuerySearchBuilder partQuerySearchBuilder = new PartQuerySearchBuilder();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("gift", "gift.name", "gift.name LIKE CONCAT('%', 'gift', '%')"),
                Arguments.of("beautiful", "gift.description", "gift.description LIKE CONCAT('%', 'beautiful', '%')"),
                Arguments.of("4 gifts", "gift.description", "gift.description LIKE CONCAT('%', '4 gifts', '%')")
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void buildTest(String value, String parameterNameInDb, String expected) {
        String actual = partQuerySearchBuilder.build(value, parameterNameInDb);

        assertEquals(expected, actual);
    }
}