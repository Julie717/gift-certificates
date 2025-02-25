package com.project.sa.validator.annotation;

import com.project.sa.util.ErrorMessageReader;
import com.project.sa.validator.annotation.impl.PaginationConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PaginationConstraintValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IncludePagination {
    String message() default ErrorMessageReader.INCORRECT_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
