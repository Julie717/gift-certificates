package com.project.sa.validator.impl;

import com.project.sa.validator.CommonValidator;

import java.util.regex.Pattern;

public class GiftCertificateNameValidator implements CommonValidator {
    private static final String NAME_PATTERN = "[\\pL\\d\\p{Punct}][\\pL\\d\\p{Punct}\\s]{0,44}";

    @Override
    public boolean isValid(String value) {
        return Pattern.matches(NAME_PATTERN, value);
    }
}