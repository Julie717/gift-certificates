package com.project.sa.querybuilder;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchBuilderTest {

    @Test
    public void buildQueryTestFull() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        parameters.put("description", "beautiful");
        parameters.put("sort", "nameGiftCertificate,-createDate");
        String expected = "FROM GiftCertificate gift JOIN FETCH gift.tags tag " +
                "WHERE gift.name LIKE CONCAT('%', 'gift', '%') AND gift.description LIKE CONCAT('%', 'beautiful', '%') " +
                "ORDER BY gift.name ASC, gift.createDate DESC";

        String actual = GiftCertificateSearchBuilder.buildQuery(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildQueryTestOnlySort() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("sort", "createDate,-nameGiftCertificate");
        String expected = "FROM GiftCertificate gift JOIN FETCH gift.tags tag " +
                "ORDER BY gift.createDate ASC, gift.name DESC";

        String actual = GiftCertificateSearchBuilder.buildQuery(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildQueryTestOnlySearch() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("description", "beautiful gift");
        String expected = "FROM GiftCertificate gift JOIN FETCH gift.tags tag " +
                "WHERE gift.description LIKE CONCAT('%', 'beautiful gift', '%') ";

        String actual = GiftCertificateSearchBuilder.buildQuery(parameters);

        assertEquals(expected, actual);
    }
}