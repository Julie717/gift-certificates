package com.project.sa.querybuilder.impl;

import com.project.sa.querybuilder.PartQueryBuilder;

public class PartQuerySearchTagBuilder implements PartQueryBuilder {
    private final static String COMMA = ",";
    private static final String START_LIKE = " LIKE CONCAT('%', '";
    private static final String END_LIKE = "', '%') OR ";

    @Override
    public String build(String value, String parameterNameInDb) {
        String[] searchParameters = value.split(COMMA);
        StringBuilder request = new StringBuilder();
        for (String param : searchParameters) {
            request.append(parameterNameInDb);
            request.append(START_LIKE);
            request.append(param);
            request.append(END_LIKE);
        }
        request.delete(request.length() - 4, request.length());
        return request.toString();
    }
}