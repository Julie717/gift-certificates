package com.project.sa.validator;

import com.project.sa.querybuilder.GiftCertificateSearchParameterName;

import java.util.Arrays;
import java.util.Map;

public class GiftCertificateSearchParameterValidator {
    public static boolean isParametersValid(Map<String, String> parameters) {
        boolean isValid = false;
        if (parameters != null && !parameters.isEmpty()) {
            isValid = parameters.entrySet().stream()
                    .allMatch(p -> IsParameterNameValid(p.getKey()) && IsParametersValueValid(p.getKey(), p.getValue()));
        }
        return isValid;
    }

    private static boolean IsParameterNameValid(String parameterName) {
        return Arrays.stream(GiftCertificateSearchParameterName.values())
                .anyMatch(p -> p.getParameterName().equals(parameterName));
    }

    private static boolean IsParametersValueValid(String parameterName, String parameterValue) {
        GiftCertificateSearchParameterName searchParameterName = GiftCertificateSearchParameterName
                .getSearchParameterName(parameterName).get();
        CommonValidator validator = searchParameterName.getValidator();
        return validator.isValid(parameterValue);
    }
}