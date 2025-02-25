package com.project.sa.service;

import com.project.sa.model.GiftCertificateDto;
import com.project.sa.model.TagDto;
import com.project.sa.util.Pagination;

import java.util.List;
import java.util.Map;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {
    /**
     * Find all gift certificates.
     *
     * @param pagination contains limit and offset for search
     * @return the list of gift certificates
     */
    List<GiftCertificateDto> findAll(Pagination pagination);

    /**
     * Find gift certificate by id.
     *
     * @param id is the id of gift certificate
     * @return the gift certificate DTO
     */
    GiftCertificateDto findById(Long id);

    /**
     * Add gift certificate to db.
     *
     * @param giftCertificateDto is the gift certificate dto that should be added
     * @return the gift certificate dto that was added
     */
    GiftCertificateDto add(GiftCertificateDto giftCertificateDto);

    /**
     * Delete gift certificate by id.
     *
     * @param id is the id of gift certificate
     */
    void deleteById(Long id);

    /**
     * Find gift certificates by list of parameters.
     *
     * @param parameters are the list of parameters that is used for searching
     * @param pagination contains limit and offset for search
     * @return the list of gift certificates DTO
     */
    List<GiftCertificateDto> findByParameters(Map<String, String> parameters, Pagination pagination);

    /**
     * Find gift certificate by tag id.
     *
     * @param idTag      is the id tag
     * @param pagination contains limit and offset for search
     * @return the list of gift certificates
     */
    List<GiftCertificateDto> findByTagId(Long idTag, Pagination pagination);

    /**
     * Find gift certificate with id = idGiftCertificate by tag id.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @param idTag             is the id of tag
     * @return the gift certificate DTO
     */
    GiftCertificateDto findGiftCertificateByTagId(Long idGiftCertificate, Long idTag);

    /**
     * Update all parameters of gift certificate.
     *
     * @param giftCertificateDto is the gift certificate DTO with new parameters
     * @return the gift certificate DTO
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Update some parameters of gift certificate.
     *
     * @param giftCertificateDto is the gift certificate DTO with new parameters
     * @return the gift certificate DTO
     */
    GiftCertificateDto patchGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Add tags to gift certificate.
     *
     * @param id      is the id of gift certificate
     * @param tagsDto are list of tag DTO that should be added
     * @return the gift certificate DTO
     */
    GiftCertificateDto addTagsToGiftCertificate(Long id, List<TagDto> tagsDto);

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate is the id gift certificate
     * @param idTag             is the id tag that should be deleted from gift certificate
     *                          with id = idGiftCertificate
     */
    void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag);
}