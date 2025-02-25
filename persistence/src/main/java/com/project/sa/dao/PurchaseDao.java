package com.project.sa.dao;

import com.project.sa.model.Purchase;

import java.util.List;

/**
 * The interface Purchase dao.
 */
public interface PurchaseDao extends CommonDao<Purchase> {
    /**
     * Find purchases by id of gift certificate.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @return the list of purchases
     */
    List<Purchase> findByIdGiftCertificate(Long idGiftCertificate);
}