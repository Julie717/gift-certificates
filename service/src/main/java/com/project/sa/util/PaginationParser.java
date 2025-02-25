package com.project.sa.util;

import java.util.Map;

public class PaginationParser {
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    public static Pagination parsePagination(Map<String, String> parameters) {
        Integer limit = Integer.parseInt(parameters.get(LIMIT));
        parameters.remove(LIMIT);
        String offsetParam = parameters.get(OFFSET);
        Integer offset = 0;
        if (offsetParam != null && !offsetParam.isEmpty()) {
            offset = Integer.parseInt(parameters.get(OFFSET));
        }
        parameters.remove(OFFSET);
        return new Pagination(limit, offset);
    }
}