package com.project.sa.dao;

import com.project.sa.config.DaoConfigTest;
import com.project.sa.dao.impl.PurchaseDaoImpl;
import com.project.sa.model.GiftCertificate;
import com.project.sa.model.Purchase;
import com.project.sa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PurchaseDaoImpl.class)
@ContextConfiguration(classes = DaoConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PurchaseDaoTest {
    private static final Purchase PURCHASE;

    @Autowired
    private PurchaseDao purchaseDao;

    static{
        PURCHASE = new Purchase();
        User user = new User();
        user.setId(2L);
        PURCHASE.setUser(user);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(2L);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        PURCHASE.setGiftCertificates(giftCertificates);
        PURCHASE.setCost(BigDecimal.valueOf(80));
        PURCHASE.setPurchaseDate(Timestamp.valueOf("2021-01-12 11:34:18"));
    }

    @Test
    void findAllTest() {
        int expectedAmountOfPurchases = 7;
        Integer limit = 10;
        Integer offset = 0;

        int actualAmountOfPurchases = purchaseDao.findAll(limit, offset).size();

        assertEquals(expectedAmountOfPurchases, actualAmountOfPurchases);
    }

    @Test
    void findByIdTest() {
        BigDecimal expectedCost = BigDecimal.valueOf(20);
        Long id = 3L;

        BigDecimal actualCost = purchaseDao.findById(id).get().getCost();

        assertEquals(expectedCost, actualCost);
    }

    @Test
    void findByIdGiftCertificateTest() {
        int expectedAmountOfPurchases = 3;
        Long id = 4L;

        int actualAmountOfPurchases = purchaseDao.findByIdGiftCertificate(id).size();

        assertEquals(expectedAmountOfPurchases, actualAmountOfPurchases);
    }

    @Test
    @Transactional
    void addTest() {
        int expectedPurchaseId = 8;

        Long actualPurchaseId = purchaseDao.add(PURCHASE).getId();

        assertEquals(expectedPurchaseId, actualPurchaseId);
    }
}