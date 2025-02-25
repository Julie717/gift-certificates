package com.project.sa.querybuilder.impl;

import com.project.sa.querybuilder.PartQueryBuilder;

public class PartQuerySearchBuilder implements PartQueryBuilder {
    private static final String START_LIKE = " LIKE CONCAT('%', '";
    private static final String END_LIKE = "', '%')";

    @Override
    public String build(String value, String parameterNameInDb) {
        StringBuilder request = new StringBuilder(parameterNameInDb);
        request.append(START_LIKE);
        request.append(value);
        request.append(END_LIKE);
        return request.toString();
    }
}