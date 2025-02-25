package com.project.sa.controller;

import com.project.sa.exception.IllegalParameterException;
import com.project.sa.exception.ResourceAlreadyExistsException;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.GiftCertificateDto;
import com.project.sa.model.TagDto;
import com.project.sa.service.GiftCertificateService;
import com.project.sa.util.HateoasLinkBuilder;
import com.project.sa.util.Pagination;
import com.project.sa.util.PaginationParser;
import com.project.sa.validator.ValidationGroup;
import com.project.sa.validator.annotation.Different;
import com.project.sa.validator.annotation.IncludePagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * The type Gift certificate controller is used to receive REST API requests
 * with mapping "/giftCertificates".
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/giftCertificates", produces = APPLICATION_JSON_VALUE)
@Validated
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Find gift certificate by id.
     *
     * @param id is the id of gift certificate
     * @return the gift certificate DTO
     * @throws ResourceNotFoundException if gift certificate isn't found
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findById(@PathVariable @Positive Long id) {
        GiftCertificateDto giftCertificate = giftCertificateService.findById(id);
        HateoasLinkBuilder.buildGiftCertificateLink(giftCertificate);
        return giftCertificate;
    }

    /**
     * Find gift certificates by parameters.
     *
     * @param parameters are the parameters for searching
     * @return the list of gift certificates
     * @throws IllegalParameterException if search parameters aren't correct
     * @throws ResourceNotFoundException if gift certificates with such parameters aren't found
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findByParameters(@NotEmpty @IncludePagination @RequestParam Map<String, String> parameters) {
        List<GiftCertificateDto> giftCertificates;
        Pagination pagination = PaginationParser.parsePagination(parameters);
        if (parameters.isEmpty()) {
            giftCertificates = giftCertificateService.findAll(pagination);
        } else {
            giftCertificates = giftCertificateService.findByParameters(parameters, pagination);
        }
        HateoasLinkBuilder.buildGiftCertificatesLink(giftCertificates);
        return giftCertificates;
    }

    /**
     * Find gift certificates by tag id.
     *
     * @param idTag      is the id of tag
     * @param pagination contains limit and offset for search
     * @return the list of gift certificate DTO
     */
    @GetMapping(value = "/tags/{idTag}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificatesByTag(@PathVariable @Positive Long idTag,
                                                              @Valid @NotNull Pagination pagination) {
        List<GiftCertificateDto> giftCertificates = giftCertificateService.findByTagId(idTag, pagination);
        HateoasLinkBuilder.buildGiftCertificatesLink(giftCertificates);
        return giftCertificates;
    }

    /**
     * Find gift certificates with id = idGiftCertificate by tag id .
     *
     * @param idGiftCertificate is the id of gift certificate
     * @param idTag             is the id of tag
     * @return the gift certificate DTO
     */
    @GetMapping(value = "/{idGiftCertificate}/tags/{idTag}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findGiftCertificatesByTag(@PathVariable @Positive Long idGiftCertificate,
                                                        @PathVariable @Positive Long idTag) {
        GiftCertificateDto giftCertificate = giftCertificateService.findGiftCertificateByTagId(idGiftCertificate, idTag);
        HateoasLinkBuilder.buildGiftCertificateLink(giftCertificate);
        return giftCertificate;
    }

    /**
     * Add gift certificate.
     *
     * @param giftCertificateDto is the gift certificate DTO that should be added
     * @return the gift certificate DTO
     * @throws ResourceAlreadyExistsException if gift certificate with such name already exists in DB
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@Validated(ValidationGroup.CreateValidation.class)
                                                 @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto giftCertificate = giftCertificateService.add(giftCertificateDto);
        HateoasLinkBuilder.buildGiftCertificateLink(giftCertificate);
        return giftCertificate;
    }

    /**
     * Add tags to gift certificate.
     *
     * @param id   is the id of gift certificate
     * @param tags the tags
     * @return the gift certificate DTO
     * @throws ResourceNotFoundException if gift certificate isn't found
     */
    @PostMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addTagsToGiftCertificate(@PathVariable @Positive Long id,
                                                       @Valid @Different @NotNull @NotEmpty @RequestBody List<TagDto> tags) {
        GiftCertificateDto giftCertificate = giftCertificateService.addTagsToGiftCertificate(id, tags);
        HateoasLinkBuilder.buildGiftCertificateLink(giftCertificate);
        return giftCertificate;
    }

    /**
     * Delete gift certificate.
     *
     * @param id is the id of gift certificate
     * @throws ResourceNotFoundException if gift certificate with such id isn't found
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable @Positive Long id) {
        giftCertificateService.deleteById(id);
    }

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @param idTag             is the id of tag
     * @throws ResourceNotFoundException if gift certificate with such tag isn't found
     */
    @DeleteMapping(value = "/{idGiftCertificate}/tags/{idTag}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagFromGiftCertificate(@PathVariable @Positive Long idGiftCertificate,
                                             @PathVariable @Positive Long idTag) {
        giftCertificateService.deleteTagFromGiftCertificate(idGiftCertificate, idTag);
    }

    /**
     * Update all parameters of gift certificate.
     *
     * @param id                 is the id of gift certificate
     * @param giftCertificateDto is the gift certificate DTO with new parameters
     * @return the gift certificate DTO with new parameters
     * @throws ResourceNotFoundException      if gift certificate with such id isn't found
     * @throws ResourceAlreadyExistsException if gift certificate with updated name exists in DB
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable
                                                    @Positive Long id,
                                                    @Validated(ValidationGroup.PutValidation.class)
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto giftCertificate = giftCertificateService.updateGiftCertificate(giftCertificateDto);
        HateoasLinkBuilder.buildGiftCertificateLink(giftCertificate);
        return giftCertificate;
    }

    /**
     * Update some parameters of gift certificate.
     *
     * @param id                 is the id of gift certificate
     * @param giftCertificateDto is the gift certificate DTO with new parameters
     * @return the gift certificate DTO with new parameters
     * @throws ResourceNotFoundException      if gift certificate with such id isn't found
     * @throws ResourceAlreadyExistsException if gift certificate with updated name exists in DB
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto patchGiftCertificate(@PathVariable
                                                   @Positive Long id,
                                                   @Validated(ValidationGroup.PatchValidation.class)
                                                   @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto giftCertificate = giftCertificateService.patchGiftCertificate(giftCertificateDto);
        HateoasLinkBuilder.buildGiftCertificateLink(giftCertificate);
        return giftCertificate;
    }
}