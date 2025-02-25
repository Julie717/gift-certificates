package com.project.sa.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceAlreadyExistsException extends RuntimeException {
    String nameResource;

    public ResourceAlreadyExistsException(String message, String nameResource) {
        super(message);
        this.nameResource = nameResource;
    }
}