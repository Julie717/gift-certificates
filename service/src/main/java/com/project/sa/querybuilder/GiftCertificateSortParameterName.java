package com.project.sa.querybuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public enum GiftCertificateSortParameterName {
    NAME("nameGiftCertificate", "gift.name"),
    CREATE_DATE("createDate", "gift.createDate");

    private final String parameterName;
    private final String parameterNameInDb;

    public static Optional<GiftCertificateSortParameterName> getSortParameterName(String name) {
        GiftCertificateSortParameterName[] sortParameterNames = GiftCertificateSortParameterName.values();
        Optional<GiftCertificateSortParameterName> sortParameterName = Arrays.stream(sortParameterNames)
                .filter(o -> o.getParameterName().equalsIgnoreCase(name)).findAny();
        return sortParameterName;
    }
}