package com.project.sa.querybuilder;

import com.project.sa.querybuilder.impl.PartQuerySearchBuilder;
import com.project.sa.querybuilder.impl.PartQuerySearchTagBuilder;
import com.project.sa.querybuilder.impl.PartQuerySortBuilder;
import com.project.sa.validator.CommonValidator;
import com.project.sa.validator.impl.GiftCertificateDescriptionValidator;
import com.project.sa.validator.impl.GiftCertificateNameValidator;
import com.project.sa.validator.impl.GiftCertificateSortValidator;
import com.project.sa.validator.impl.TagNameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum GiftCertificateSearchParameterName {
    NAME("nameGiftCertificate", "gift.name",
            new GiftCertificateNameValidator(), new PartQuerySearchBuilder()),
    DESCRIPTION("description", "gift.description",
            new GiftCertificateDescriptionValidator(), new PartQuerySearchBuilder()),
    SORT("sort", null, new GiftCertificateSortValidator(), new PartQuerySortBuilder()),
    NAME_TAG("nameTag", "tag.name",
            new TagNameValidator(), new PartQuerySearchTagBuilder());

    private final String parameterName;
    private final String parameterNameInDb;
    private final CommonValidator validator;
    private final PartQueryBuilder partQueryBuilder;

    public static Optional<GiftCertificateSearchParameterName> getSearchParameterName(String name) {
        GiftCertificateSearchParameterName[] parameterNames = GiftCertificateSearchParameterName.values();
        Optional<GiftCertificateSearchParameterName> resultParameterName = Arrays.stream(parameterNames)
                .filter(o -> o.getParameterName().equalsIgnoreCase(name)).findAny();
        return resultParameterName;
    }
}