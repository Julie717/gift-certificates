package com.project.sa.model;

import com.project.sa.util.ErrorMessageReader;
import com.project.sa.validator.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto extends RepresentationModel<TagDto> implements Serializable {
    @NotNull(groups = ValidationGroup.PutValidation.class)
    @Positive(groups = ValidationGroup.PutValidation.class)
    Long id;

    @NotBlank
    @Size(min = 2, max = 45, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String name;
}