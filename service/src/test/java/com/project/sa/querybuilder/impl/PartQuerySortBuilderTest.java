package com.project.sa.querybuilder.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartQuerySortBuilderTest {
    PartQuerySortBuilder partQuerySortBuilder = new PartQuerySortBuilder();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("nameGiftCertificate,-createDate", null, "ORDER BY gift.name ASC, gift.createDate DESC"),
                Arguments.of("createDate", null, "ORDER BY gift.createDate ASC"),
                Arguments.of("createDate,-nameGiftCertificate", null, "ORDER BY gift.createDate ASC, gift.name DESC")
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void buildTest(String value, String parameterNameInDb, String expected) {
        String actual = partQuerySortBuilder.build(value, parameterNameInDb);

        assertEquals(expected, actual);
    }
}