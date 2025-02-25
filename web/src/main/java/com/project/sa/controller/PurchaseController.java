package com.project.sa.controller;

import com.project.sa.model.PurchaseRequestDto;
import com.project.sa.model.PurchaseResponseDto;
import com.project.sa.service.PurchaseService;
import com.project.sa.util.HateoasLinkBuilder;
import com.project.sa.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * The type Purchase controller is used to receive REST API requests
 * with mapping "/purchases".
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/purchases", produces = APPLICATION_JSON_VALUE)
@Validated
public class PurchaseController {
    private final PurchaseService purchaseService;

    /**
     * Find all purchases.
     *
     * @param pagination contains limit and offset for search
     * @return the list of purchases response DTO
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseResponseDto> findAll(@Valid @NotNull Pagination pagination) {
        List<PurchaseResponseDto> purchases = purchaseService.findAll(pagination);
        HateoasLinkBuilder.buildPurchasesLink(purchases);
        return purchases;
    }

    /**
     * Find purchase by id.
     *
     * @param id is the id of purchase
     * @return the purchase response DTO
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseResponseDto findById(@PathVariable @Positive Long id) {
        PurchaseResponseDto purchase = purchaseService.findById(id);
        HateoasLinkBuilder.buildPurchaseLink(purchase);
        return purchase;
    }

    /**
     * Provides the ability to make purchase for user.
     *
     * @param purchaseDto is the purchase DTO that contains id user and list of gift certificate's ids
     * @return the purchase response DTO - information about new created purchase
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PurchaseResponseDto makePurchase(@RequestBody @Valid PurchaseRequestDto purchaseDto) {
        PurchaseResponseDto purchase = purchaseService.makePurchase(purchaseDto);
        HateoasLinkBuilder.buildPurchaseLink(purchase);
        return purchase;
    }
}