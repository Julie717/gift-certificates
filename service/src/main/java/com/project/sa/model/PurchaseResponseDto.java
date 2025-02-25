package com.project.sa.model;

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

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseResponseDto extends RepresentationModel<PurchaseResponseDto> implements Serializable {
    Long id;

    @Digits(integer = 5, fraction = 2)
    BigDecimal cost;

    @JsonFormat(timezone = "Europe/Minsk", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Timestamp purchaseDate;

    Long idUser;

    @NotEmpty
    @NotNull
    List<Long> idGiftCertificates;
}