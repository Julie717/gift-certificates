package com.project.sa.service;

import com.project.sa.model.PurchaseRequestDto;
import com.project.sa.model.PurchaseResponseDto;
import com.project.sa.util.Pagination;

import java.util.List;

/**
 * The interface Purchase service.
 */
public interface PurchaseService {
    /**
     * Find all purchases.
     *
     * @param pagination contains limit and offset for search
     * @return the list of purchases
     */
    List<PurchaseResponseDto> findAll(Pagination pagination);

    /**
     * Find purchase by id.
     *
     * @param id is the id of purchase
     * @return the purchase response DTO
     */
    PurchaseResponseDto findById(Long id);

    /**
     * Create purchase for user.
     *
     * @param purchase is the purchase that contains id user and list of gift certificate's ids
     * @return the purchase response DTO
     */
    PurchaseResponseDto makePurchase(PurchaseRequestDto purchase);
}