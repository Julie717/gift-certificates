package com.project.sa.validator.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSortValidatorTest {
    GiftCertificateSortValidator sortValidator = new GiftCertificateSortValidator();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("nameGiftCertificate,-createDate", true),
                Arguments.of("-nameGiftCertificate", true),
                Arguments.of("createDate", true),
                Arguments.of("gift", false),
                Arguments.of("nameGiftCertificate,-createDate,lastUpdate", false)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(String sort, boolean expected) {
        boolean actual = sortValidator.isValid(sort);

        assertEquals(expected, actual);
    }
}