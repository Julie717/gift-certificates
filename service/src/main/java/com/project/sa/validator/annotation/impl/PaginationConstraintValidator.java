package com.project.sa.validator.annotation.impl;

import com.project.sa.validator.annotation.IncludePagination;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class PaginationConstraintValidator implements ConstraintValidator<IncludePagination, Map<String, String>> {
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";
    private static final String NUMBER = "\\d+";

    @Override
    public void initialize(IncludePagination constraintAnnotation) {
    }

    @Override
    public boolean isValid(Map<String, String> value, ConstraintValidatorContext context) {
        String limit = value.get(LIMIT);
        String offset = value.get(OFFSET);
        boolean isValid = limit != null && !limit.isEmpty();
        isValid = isValid && Pattern.matches(NUMBER, limit);
        if (offset != null && !offset.isEmpty()) {
            isValid = isValid && Pattern.matches(NUMBER, offset);
        }
        return isValid;
    }
}