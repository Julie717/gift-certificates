package com.project.sa.validator.impl;

import com.project.sa.validator.CommonValidator;
import java.util.regex.Pattern;

public class GiftCertificateDescriptionValidator implements CommonValidator {
    private static final String DESCRIPTION_PATTERN = "[\\pL\\d\\p{Punct}][\\pL\\d\\p{Punct}\\s]{0,43}";

    @Override
    public boolean isValid(String value) {
        return Pattern.matches(DESCRIPTION_PATTERN, value);
    }
}