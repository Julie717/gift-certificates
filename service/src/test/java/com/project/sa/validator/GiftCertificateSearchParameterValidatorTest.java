package com.project.sa.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchParameterValidatorTest {

    public static Stream<Arguments> data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("nameGiftCertificate", "gift");
        parameters1.put("description", "beautiful");
        parameters1.put("sort", "nameGiftCertificate,-createDate");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("newDescription", "beautiful");
        parameters2.put("sort", "nameGiftCertificate,-createDate");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("description", "beautiful");
        parameters3.put("nameGiftCertificate", "");
        Map<String, String> parameters4 = new HashMap<>();
        Map<String, String> parameters5 = new HashMap<>();
        parameters5.put("nameTag", "beautiful,gift");
        parameters5.put("nameGiftCertificate", "a");
        return Stream.of(
                Arguments.of(parameters1, true),
                Arguments.of(parameters2, false),
                Arguments.of(parameters3, false),
                Arguments.of(parameters4, false),
                Arguments.of(parameters5, true)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isParametersValidTest(Map<String, String> parameters, boolean expected) {
        boolean actual = GiftCertificateSearchParameterValidator.isParametersValid(parameters);

        assertEquals(expected, actual);
    }
}