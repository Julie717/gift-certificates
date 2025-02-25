package com.project.sa.model.converter.impl;

import com.project.sa.model.GiftCertificate;
import com.project.sa.model.GiftCertificateDto;
import com.project.sa.model.Tag;
import com.project.sa.model.TagDto;
import com.project.sa.model.converter.CommonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class GiftCertificateConverterImpl implements CommonConverter<GiftCertificate, GiftCertificateDto> {
    private final TagConverterImpl tagConverter;

    @Override
    public GiftCertificateDto convertTo(GiftCertificate entity) {
        List<TagDto> tags = null;
        if (entity.getTags() != null) {
            tags = tagConverter.convertTo(entity.getTags());
        }
        return new GiftCertificateDto(entity.getId(),
                entity.getName(), entity.getDescription(), entity.getPrice(), entity.getDuration(),
                entity.getCreateDate(), entity.getLastUpdateDate(), tags);
    }

    @Override
    public GiftCertificate convertFrom(GiftCertificateDto entity) {
        GiftCertificate giftCertificate = new GiftCertificate();
        if (entity.getId() != null) {
            giftCertificate.setId(entity.getId());
        }
        giftCertificate.setName(entity.getName());
        giftCertificate.setDescription(entity.getDescription());
        giftCertificate.setPrice(entity.getPrice());
        giftCertificate.setDuration(entity.getDuration());
        if (entity.getTags() != null) {
            List<Tag> tags = tagConverter.convertFrom(entity.getTags());
            giftCertificate.setTags(tags);
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificateDto> convertTo(List<GiftCertificate> entities) {
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        if (!entities.isEmpty()) {
            entities.forEach(g -> giftCertificatesDto.add(convertTo(g)));
        }
        return giftCertificatesDto;
    }

    @Override
    public List<GiftCertificate> convertFrom(List<GiftCertificateDto> entities) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (!entities.isEmpty()) {
            entities.forEach(g -> giftCertificates.add(convertFrom(g)));
        }
        return giftCertificates;
    }
}