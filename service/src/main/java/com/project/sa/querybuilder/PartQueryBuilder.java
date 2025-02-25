package com.project.sa.querybuilder;

/**
 * The interface Part query builder.
 */
public interface PartQueryBuilder {
    /**
     * Build query for search by parameters.
     *
     * @param value             is the value of parameter that is used for searching
     * @param parameterNameInDb is the parameter name in db for Hibernate
     * @return the string
     */
    String build(String value, String parameterNameInDb);
}