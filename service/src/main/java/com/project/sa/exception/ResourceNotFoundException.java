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
public class ResourceNotFoundException extends RuntimeException {
    Long idResource;
    String nameResource;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Long idResource) {
        super(message);
        this.idResource = idResource;
    }

    public ResourceNotFoundException(String message, Long idResource, String nameResource) {
        super(message);
        this.idResource = idResource;
        this.nameResource = nameResource;
    }
}