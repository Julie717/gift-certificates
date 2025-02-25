package com.project.sa.validator.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagNameValidatorTest {
    TagNameValidator tagNameValidator = new TagNameValidator();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("r", true),
                Arguments.of("yn,if", true),
                Arguments.of("4_gifts", true),
                Arguments.of("wonderful gift,jumping", true),
                Arguments.of("Winter is a nice season. Some people like summer or autumn; but others like winter. " +
                        "December; January and February are winter months. But in some places winter begins " +
                        "in November and ends in March or April. The nights in winter are very long; and it is", false)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(String name, boolean expected) {
        boolean actual = tagNameValidator.isValid(name);

        assertEquals(expected, actual);
    }
}
