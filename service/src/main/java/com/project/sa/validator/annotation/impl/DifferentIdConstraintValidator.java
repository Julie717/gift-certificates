package com.project.sa.validator.annotation.impl;

import com.project.sa.validator.annotation.Different;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DifferentIdConstraintValidator implements ConstraintValidator<Different, List<Long>> {
    @Override
    public void initialize(Different constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (value != null && !value.isEmpty()) {
            Map<Long, Long> ids = value.stream().filter(v -> v != null)
                    .collect(Collectors.groupingBy(v -> v, Collectors.counting()));
            isValid = ids.entrySet().stream().allMatch(id -> id.getValue() == 1);
        }
        return isValid;
    }
}