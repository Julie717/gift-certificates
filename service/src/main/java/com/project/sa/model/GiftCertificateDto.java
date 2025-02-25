package com.project.sa.model;

import com.project.sa.util.ErrorMessageReader;
import com.project.sa.validator.ValidationGroup;
import com.project.sa.validator.annotation.Different;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> implements Serializable {
    Long id;

    @NotBlank(groups = {ValidationGroup.CreateValidation.class, ValidationGroup.PutValidation.class})
    @Size(min = 2, max = 45, message = ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_NAME_SIZE)
    String name;

    @NotBlank(groups = {ValidationGroup.CreateValidation.class, ValidationGroup.PutValidation.class})
    @Size(min = 1, max = 1000, message = ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_DESCRIPTION_SIZE)
    String description;

    @NotNull(groups = {ValidationGroup.CreateValidation.class, ValidationGroup.PutValidation.class})
    @Positive
    @Digits(integer = 5, fraction = 2)
    BigDecimal price;

    @NotNull(groups = {ValidationGroup.CreateValidation.class, ValidationGroup.PutValidation.class})
    @Positive
    @Min(1)
    Integer duration;

    @JsonFormat(timezone = "Europe/Minsk", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Timestamp createDate;

    @JsonFormat(timezone = "Europe/Minsk", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Timestamp lastUpdateDate;

    @Different
    @Valid
    List<TagDto> tags;
}