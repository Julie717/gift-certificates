package com.project.sa.service.impl;

import com.project.sa.dao.PurchaseDao;
import com.project.sa.dao.UserDao;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.GiftCertificate;
import com.project.sa.model.Purchase;
import com.project.sa.model.PurchaseRequestDto;
import com.project.sa.model.PurchaseResponseDto;
import com.project.sa.model.User;
import com.project.sa.model.converter.impl.GiftCertificateConverterImpl;
import com.project.sa.model.converter.impl.PurchaseResponseConverterImpl;
import com.project.sa.service.GiftCertificateService;
import com.project.sa.service.PurchaseService;
import com.project.sa.util.ErrorMessageReader;
import com.project.sa.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseDao purchaseDao;
    private final UserDao userDao;
    private final PurchaseResponseConverterImpl purchaseResponseConverter;
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateConverterImpl giftCertificateConverter;

    @Override
    public List<PurchaseResponseDto> findAll(Pagination pagination) {
        List<Purchase> purchases = purchaseDao.findAll(pagination.getLimit(), pagination.getOffset());
        return purchaseResponseConverter.convertTo(purchases);
    }

    @Override
    public PurchaseResponseDto findById(Long id) {
        Purchase purchase = purchaseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Purchase.class.getSimpleName()));
        return purchaseResponseConverter.convertTo(purchase);
    }

    @Override
    @Transactional
    public PurchaseResponseDto makePurchase(PurchaseRequestDto purchaseRequestDto) {
        List<Long> idGiftCertificates = purchaseRequestDto.getIdGiftCertificates();
        Long idUser = purchaseRequestDto.getIdUser();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        idGiftCertificates.stream().map(giftCertificateService::findById)
                .forEach(g -> giftCertificates.add(giftCertificateConverter.convertFrom(g)));
        User user = userDao.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, idUser,
                        User.class.getSimpleName()));
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setGiftCertificates(giftCertificates);
        purchase.setCost(countCost(giftCertificates));
        purchase = purchaseDao.add(purchase);
        return purchaseResponseConverter.convertTo(purchase);
    }

    private BigDecimal countCost(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}