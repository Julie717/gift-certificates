package com.project.sa.validator.annotation;

import com.project.sa.model.TagDto;
import com.project.sa.validator.annotation.impl.DifferentTagsConstraintValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferentTagsConstraintValidatorTest {
    DifferentTagsConstraintValidator tagsConstraintValidator = new DifferentTagsConstraintValidator();

    public static Stream<Arguments> data() {
        List<TagDto> value1 = new ArrayList<>();
        value1.add(new TagDto(1L, "gift"));
        value1.add(new TagDto(2L, "wonderful"));
        List<TagDto> value2 = new ArrayList<>();
        value2.add(new TagDto(1L, "gift"));
        value2.add(new TagDto(2L, "wonderful"));
        value2.add(new TagDto(1L, "gift"));
        return Stream.of(
                Arguments.of(value1, true),
                Arguments.of(value2, false),
                Arguments.of(new ArrayList<>(), true),
                Arguments.of(null, true)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(List<TagDto> value, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = tagsConstraintValidator.isValid(value, context);

        assertEquals(expected, actual);
    }
}