package com.project.sa.model.converter.impl;

import com.project.sa.model.GiftCertificate;
import com.project.sa.model.Purchase;
import com.project.sa.model.PurchaseRequestDto;
import com.project.sa.model.User;
import com.project.sa.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseRequestConverterImpl implements CommonConverter<Purchase, PurchaseRequestDto> {
    @Override
    public PurchaseRequestDto convertTo(Purchase entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Purchase convertFrom(PurchaseRequestDto entity) {
        User user = new User();
        user.setId(entity.getIdUser());
        List<GiftCertificate> giftCertificates = entity.getIdGiftCertificates().stream().map(g -> {
                    GiftCertificate giftCertificate = new GiftCertificate();
                    giftCertificate.setId(g);
                    return giftCertificate;
                }
        ).collect(Collectors.toList());
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setGiftCertificates(giftCertificates);
        return purchase;
    }

    @Override
    public List<PurchaseRequestDto> convertTo(List<Purchase> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Purchase> convertFrom(List<PurchaseRequestDto> entities) {
        List<Purchase> purchases = new ArrayList<>();
        entities.forEach(t -> purchases.add(convertFrom(t)));
        return purchases;
    }
}