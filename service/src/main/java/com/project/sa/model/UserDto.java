package com.project.sa.model;

import com.project.sa.util.ErrorMessageReader;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends RepresentationModel<UserDto> implements Serializable {
    Long id;

    @NotBlank
    @Size(min = 2, max = 20, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String name;

    @NotBlank
    @Size(min = 2, max = 50, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String surname;

    @Valid
    List<PurchaseResponseDto> purchases;
}
