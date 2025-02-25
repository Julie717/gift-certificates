package com.project.sa.validator.annotation;

import com.project.sa.validator.annotation.impl.PaginationConstraintValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationConstraintValidatorTest {
    PaginationConstraintValidator paginationValidator = new PaginationConstraintValidator();

    public static Stream<Arguments> data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("limit", "5");
        parameters1.put("offset", "4");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("limit", "3");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("limit", "14");
        parameters3.put("offset", "10");
        Map<String, String> parameters4 = new HashMap<>();
        Map<String, String> parameters5 = new HashMap<>();
        parameters5.put("limit", "-10");
        Map<String, String> parameters6 = new HashMap<>();
        parameters6.put("limit", "14");
        parameters6.put("offset", "10d");
        Map<String, String> parameters7 = new HashMap<>();
        parameters7.put("limit", "max");
        return Stream.of(
                Arguments.of(parameters1, true),
                Arguments.of(parameters2, true),
                Arguments.of(parameters3, true),
                Arguments.of(parameters4, false),
                Arguments.of(parameters5, false),
                Arguments.of(parameters6, false),
                Arguments.of(parameters7, false)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(Map<String, String> parameters, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = paginationValidator.isValid(parameters, context);

        assertEquals(expected, actual);
    }
}