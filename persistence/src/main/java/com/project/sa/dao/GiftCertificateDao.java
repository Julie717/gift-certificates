package com.project.sa.dao;

import com.project.sa.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends CommonDao<GiftCertificate> {
    /**
     * Find gift certificate by name in Db.
     *
     * @param nameGiftCertificate is the name gift certificate
     * @return the optional of gift certificate
     */
    Optional<GiftCertificate> findByName(String nameGiftCertificate);

    /**
     * Find gift certificates by parameters in Db.
     *
     * @param queryLastPart is the query that uses to find gift certificates
     * @param limit         is the maximum amount of gift certificates that can be found
     * @param offset        is the number of gift certificate from which starts search
     * @return the list of gift certificates
     */
    List<GiftCertificate> findByParameters(String queryLastPart, Integer limit, Integer offset);

    /**
     * Find gift certificates by tag id.
     *
     * @param idTag  is  the id of tag
     * @param limit  is the maximum amount of gift certificates that can be found
     * @param offset is the number of gift certificate from which starts search
     * @return the list of gift certificates that was found
     */
    List<GiftCertificate> findByTagId(Long idTag, Integer limit, Integer offset);

    /**
     * Find gift certificate with id = idGiftCertificate and tag id = idTag.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @param idTag             is the id of tag
     * @return the optional value of gift certificate
     */
    Optional<GiftCertificate> findByTagIdInGiftCertificate(Long idGiftCertificate, Long idTag);

    /**
     * Update parameter of a gift certificate in Db.
     *
     * @param giftCertificate is the gift certificate that should be updated
     * @return the gift certificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @param idTag             is the id of tag that should be deleted from gift certificate
     *                          with id = idGiftCertificate
     */
    void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag);
}