package com.project.sa.querybuilder.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartQuerySearchTagBuilderTest {
    PartQuerySearchTagBuilder partQuerySearchTagBuilder = new PartQuerySearchTagBuilder();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("gift,beautiful,a", "tag.name", "tag.name LIKE CONCAT('%', 'gift', '%') " +
                        "OR tag.name LIKE CONCAT('%', 'beautiful', '%') OR tag.name LIKE CONCAT('%', 'a', '%')"),
                Arguments.of("first", "tag.name", "tag.name LIKE CONCAT('%', 'first', '%')"),
                Arguments.of("4 gifts,s", "tag.name", "tag.name LIKE CONCAT('%', '4 gifts', '%') " +
                        "OR tag.name LIKE CONCAT('%', 's', '%')")
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void buildTest(String value, String parameterNameInDb, String expected) {
        String actual = partQuerySearchTagBuilder.build(value, parameterNameInDb);

        assertEquals(expected, actual);
    }
}