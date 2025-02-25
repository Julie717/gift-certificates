package com.project.sa.service.impl;

import com.project.sa.dao.GiftCertificateDao;
import com.project.sa.dao.PurchaseDao;
import com.project.sa.exception.IllegalParameterException;
import com.project.sa.exception.ResourceAlreadyExistsException;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.GiftCertificate;
import com.project.sa.model.GiftCertificateDto;
import com.project.sa.model.Tag;
import com.project.sa.model.TagDto;
import com.project.sa.model.Purchase;
import com.project.sa.model.converter.impl.GiftCertificateConverterImpl;
import com.project.sa.model.converter.impl.TagConverterImpl;
import com.project.sa.querybuilder.GiftCertificateSearchBuilder;
import com.project.sa.service.GiftCertificateService;
import com.project.sa.service.TagService;
import com.project.sa.util.ErrorMessageReader;
import com.project.sa.util.Pagination;
import com.project.sa.validator.GiftCertificateSearchParameterValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final PurchaseDao purchaseDao;
    private final TagService tagService;
    private final GiftCertificateConverterImpl giftCertificateConverter;
    private final TagConverterImpl tagConverter;

    @Override
    public List<GiftCertificateDto> findAll(Pagination pagination) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll(pagination.getLimit(), pagination.getOffset());
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        id, GiftCertificate.class.getSimpleName()));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findByParameters(Map<String, String> parameters, Pagination pagination) {
        if (!GiftCertificateSearchParameterValidator.isParametersValid(parameters)) {
            throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
        }
        String request = GiftCertificateSearchBuilder.buildQuery(parameters);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByParameters(request, pagination.getLimit(),
                pagination.getOffset());
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND_BY_PARAMS);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public List<GiftCertificateDto> findByTagId(Long idTag, Pagination pagination) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByTagId(idTag,
                pagination.getLimit(), pagination.getOffset());
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND, idTag);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto findGiftCertificateByTagId(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = giftCertificateDao.findByTagIdInGiftCertificate(idGiftCertificate, idTag)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND, idTag));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> currentGiftCertificate = giftCertificateDao.findByName(giftCertificateDto.getName());
        if (currentGiftCertificate.isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                    giftCertificateDto.getName());
        }
        if (giftCertificateDto.getTags() != null && !giftCertificateDto.getTags().isEmpty()) {
            List<TagDto> tags = tagService.findByRangeNames(giftCertificateDto.getTags());
            List<TagDto> newTags = receiveNewTags(tagConverter.convertFrom(tags), giftCertificateDto.getTags());
            if (!newTags.isEmpty()) {
                throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
            }
            giftCertificateDto.setTags(tags);
        }
        GiftCertificate giftCertificate = giftCertificateConverter.convertFrom(giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateDao.add(giftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto addTagsToGiftCertificate(Long id, List<TagDto> tagsDto) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        id, GiftCertificate.class.getSimpleName()));
        List<TagDto> newTags = receiveNewTags(giftCertificate.getTags(), tagsDto);
        if (newTags.isEmpty()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.TAG_ALREADY_EXISTS_IN_GIFT_CERTIFICATE,
                    giftCertificate.getName());
        }
        List<Tag> tags = giftCertificate.getTags();
        addNewTagsToGiftCertificate(giftCertificate, tagsDto);
        if (tags != null) {
            tagsDto.stream().filter(t -> tags.stream()
                    .anyMatch(tag -> !tag.getName().equals(t.getName()))).forEach(
                    t -> tags.add(tagConverter.convertFrom(t))
            );
        }
        return giftCertificateConverter.convertTo(giftCertificateDao.update(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        GiftCertificate.class.getSimpleName()));
        List<Purchase> purchases = purchaseDao.findByIdGiftCertificate(id);
        if (purchases != null && !purchases.isEmpty()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATES_USED, giftCertificate.getName());
        }
        giftCertificateDao.delete(giftCertificate);
    }

    @Override
    @Transactional
    public void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(idGiftCertificate).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, idGiftCertificate,
                        GiftCertificate.class.getSimpleName()));
        boolean isExist = false;
        if (giftCertificate.getTags() != null || !giftCertificate.getTags().isEmpty()) {
            isExist = giftCertificate.getTags().stream().anyMatch(t -> t.getId().equals(idTag));
        }
        if (!isExist) {
            throw new ResourceNotFoundException(ErrorMessageReader.TAG_IN_GIFT_CERTIFICATE_NOT_FOUND, idTag);
        }
        giftCertificateDao.deleteTagFromGiftCertificate(idGiftCertificate, idTag);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        giftCertificateDto.getId(), GiftCertificate.class.getSimpleName()));
        Optional<GiftCertificate> giftCertificateWithNewNameInDB = giftCertificateDao
                .findByName(giftCertificateDto.getName());
        if (giftCertificateWithNewNameInDB.isPresent()) {
            if (!giftCertificateWithNewNameInDB.get().getId().equals(giftCertificateDto.getId())) {
                throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                        giftCertificateDto.getName());
            }
        }
        boolean isCorrectTags = checkTags(giftCertificateDto.getTags());
        if (!isCorrectTags) {
            throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
        }
        GiftCertificate newGiftCertificate = giftCertificateConverter.convertFrom(giftCertificateDto);
        newGiftCertificate.setCreateDate(currentGiftCertificate.getCreateDate());
        newGiftCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        return giftCertificateConverter.convertTo(giftCertificateDao.update(newGiftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto patchGiftCertificate(GiftCertificateDto giftCertificateDto) {
        //Check existence of certificate with this id
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        giftCertificateDto.getId(), GiftCertificate.class.getSimpleName()));
        //Check existence of certificate with new name. If there are found any certificate with the same name,
        //certificate won't be added
        if (giftCertificateDto.getName() != null && !giftCertificateDto.getName().isEmpty()) {
            Optional<GiftCertificate> giftCertificateWithNewNameInDB = giftCertificateDao
                    .findByName(giftCertificateDto.getName());
            if (giftCertificateWithNewNameInDB.isPresent()) {
                if (!giftCertificateWithNewNameInDB.get().getId().equals(giftCertificateDto.getId())) {
                    throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                            giftCertificateDto.getName());
                }
            }
        }
        GiftCertificate newGiftCertificate = mergeCurrentAndNewGiftCertificate(currentGiftCertificate, giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateDao.update(newGiftCertificate));
    }

    private GiftCertificate mergeCurrentAndNewGiftCertificate(GiftCertificate currentGiftCertificate,
                                                              GiftCertificateDto newGiftCertificate) {
        if (newGiftCertificate.getName() != null && !newGiftCertificate.getName().isEmpty()) {
            currentGiftCertificate.setName(newGiftCertificate.getName());
        }
        if (newGiftCertificate.getDescription() != null && !newGiftCertificate.getDescription().isEmpty()) {
            currentGiftCertificate.setDescription(newGiftCertificate.getDescription());
        }
        if (newGiftCertificate.getPrice() != null) {
            currentGiftCertificate.setPrice(newGiftCertificate.getPrice());
        }
        if (newGiftCertificate.getDuration() != null) {
            currentGiftCertificate.setDuration(newGiftCertificate.getDuration());
        }
        if (newGiftCertificate.getTags() != null) {
            addNewTagsToGiftCertificate(currentGiftCertificate, newGiftCertificate.getTags());
        }
        return currentGiftCertificate;
    }

    private List<TagDto> receiveNewTags(List<Tag> currentTags, List<TagDto> tagsDto) {
        List<TagDto> newTags = tagsDto;
        if (currentTags != null && !currentTags.isEmpty()) {
            newTags = tagsDto.stream()
                    .filter(t -> currentTags.stream()
                            .noneMatch(c -> c.getName().equals(t.getName())))
                    .collect(Collectors.toList());
        }
        return newTags;
    }

    private void addNewTagsToGiftCertificate(GiftCertificate currentGiftCertificate, List<TagDto> tagsDto) {
        if (tagsDto != null) {
            List<Tag> tags = new ArrayList<>();
            List<TagDto> existTags = tagService.findByRangeNames(tagsDto);
            for (TagDto tag : tagsDto) {
                existTags.stream().filter(t -> t.getName().equals(tag.getName()))
                        .forEach(t -> tags.add(tagConverter.convertFrom(t)));
            }
            tagsDto.stream().filter(t -> existTags.stream()
                    .noneMatch(tag -> tag.getName().equals(t.getName())))
                    .forEach(t -> tags.add(tagConverter.convertFrom(t)));
            currentGiftCertificate.setTags(tags);
        }
    }

    private boolean checkTags(List<TagDto> newTags) {
        boolean isCorrect = true;
        if (newTags != null) {
            List<TagDto> tags = tagService.findByRangeNames(newTags);
            isCorrect = newTags.stream().allMatch(t ->
                    tags.stream().anyMatch(tag -> tag.getId().equals(t.getId())));
            if (isCorrect) {
                isCorrect = newTags.stream().allMatch(t ->
                        tags.stream()
                                .noneMatch(tag -> tag.getName().equals(t.getName()) && !tag.getId().equals(t.getId())));
            }
        }
        return isCorrect;
    }
}