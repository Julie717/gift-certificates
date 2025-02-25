package com.project.sa.querybuilder.impl;

import com.project.sa.querybuilder.GiftCertificateSortParameterName;
import com.project.sa.querybuilder.PartQueryBuilder;

public class PartQuerySortBuilder implements PartQueryBuilder {
    private final static String COMMA = ",";
    private final static String SPACE = " ";
    private final static char SIGN_MINUS = '-';
    private static final String ORDER_BY = "ORDER BY ";
    private final static String ASC = " ASC";
    private final static String DESC = " DESC";

    @Override
    public String build(String value, String parameterNameInDb) {
        String[] sortParameters = value.split(COMMA);
        StringBuilder request = new StringBuilder(ORDER_BY);
        for (String param : sortParameters) {
            param = param.trim();
            char firstSymbol = param.charAt(0);
            String paramName = param;
            String typeSort = ASC;
            if (firstSymbol == SIGN_MINUS) {
                paramName = param.substring(1);
                typeSort = DESC;
            }
            GiftCertificateSortParameterName sortParameterName = GiftCertificateSortParameterName
                    .getSortParameterName(paramName).get();
            request.append(sortParameterName.getParameterNameInDb());
            request.append(typeSort);
            request.append(COMMA);
            request.append(SPACE);
        }
        request.delete(request.length() - 2, request.length());
        return request.toString();
    }
}