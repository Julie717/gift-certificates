package com.project.sa.querybuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GiftCertificateSearchBuilder {
    private final static String BEGIN_QUERY="FROM GiftCertificate gift JOIN FETCH gift.tags tag ";
    private final static String WHERE = "WHERE ";
    private static final String SORT = "ORDER BY";
    private final static String AND = " AND ";
    private final static String SPACE = " ";

    private static List<String> buildQueryPart(Map<String, String> parameters) {
        List<String> queryParts = new ArrayList<>();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            GiftCertificateSearchParameterName parameterName = GiftCertificateSearchParameterName
                    .getSearchParameterName(param.getKey()).get();
            String queryPart = parameterName.getPartQueryBuilder().build(param.getValue(),
                    parameterName.getParameterNameInDb());
            queryParts.add(queryPart);
        }
        return queryParts;
    }

    public static String buildQuery(Map<String, String> parameters) {
        StringBuilder sqlQuery;
        List<String> requestParts = GiftCertificateSearchBuilder.buildQueryPart(parameters);
        sqlQuery = new StringBuilder(BEGIN_QUERY);
        if (requestParts.stream().anyMatch(r -> !r.contains(SORT))) {
            sqlQuery.append(WHERE);
            requestParts.stream().filter(r -> !r.contains(SORT)).forEach(r -> sqlQuery.append(r).append(AND));
            sqlQuery.replace(sqlQuery.length() - AND.length(), sqlQuery.length(), SPACE);
        }
        requestParts.stream().filter(r -> r.contains(SORT)).forEach(sqlQuery::append);
        return sqlQuery.toString();
    }
}