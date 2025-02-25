package com.project.sa.util;

import com.project.sa.controller.GiftCertificateController;
import com.project.sa.controller.PurchaseController;
import com.project.sa.controller.TagController;
import com.project.sa.controller.UserController;
import com.project.sa.model.GiftCertificateDto;
import com.project.sa.model.PurchaseResponseDto;
import com.project.sa.model.TagDto;
import com.project.sa.model.UserDto;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class HateoasLinkBuilder {
    private static final String USER = "user_";
    private static final String GIFT_CERTIFICATE = "giftCertificate_";
    private static final String TAG = "tag_";
    private static final String PURCHASE = "purchase_";

    public static void buildTagsLink(List<TagDto> tags) {
        if (tags != null && !tags.isEmpty()) {
            tags.forEach(HateoasLinkBuilder::buildTagLink);
        }
    }

    public static void buildTagLink(TagDto tag) {
        tag.add(linkTo(TagController.class).slash(tag.getId()).withRel(TAG + tag.getId()));
    }

    public static void buildGiftCertificateLink(GiftCertificateDto giftCertificate) {
        giftCertificate.add(linkTo(GiftCertificateController.class).slash(giftCertificate.getId())
                .withRel(GIFT_CERTIFICATE + giftCertificate.getId()));
        List<TagDto> tags = giftCertificate.getTags();
        buildTagsLink(tags);
    }

    public static void buildGiftCertificatesLink(List<GiftCertificateDto> giftCertificates) {
        if (giftCertificates != null && !giftCertificates.isEmpty()) {
            giftCertificates.forEach(HateoasLinkBuilder::buildGiftCertificateLink);
        }
    }

    public static void buildPurchaseLink(PurchaseResponseDto purchase) {
        purchase.add(linkTo(PurchaseController.class).slash(purchase.getId()).withRel(PURCHASE + purchase.getId()));
        List<Long> certificatesId = purchase.getIdGiftCertificates();
        List<Link> links = certificatesId.stream().map(id -> linkTo(GiftCertificateController.class)
                .slash(id).withRel(GIFT_CERTIFICATE + id))
                .collect(Collectors.toList());
        purchase.add(links);
        purchase.add(linkTo(UserController.class).slash(purchase.getIdUser()).
                withRel(USER + purchase.getIdUser()));
    }

    public static void buildPurchasesLink(List<PurchaseResponseDto> purchases) {
        if (purchases != null && !purchases.isEmpty()) {
            purchases.forEach(HateoasLinkBuilder::buildPurchaseLink);
        }
    }

    public static void buildUserLink(UserDto user) {
        user.add(linkTo(UserController.class).slash(user.getId()).withRel(USER + user.getId()));
        buildPurchasesLink(user.getPurchases());
    }

    public static void buildUsersLink(List<UserDto> users) {
        if (users != null && !users.isEmpty()) {
            users.forEach(HateoasLinkBuilder::buildUserLink);
        }
    }
}