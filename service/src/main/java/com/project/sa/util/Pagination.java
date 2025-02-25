package com.project.sa.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pagination {
    private static final Integer DEFAULT_OFFSET = 0;

    @NotNull
    @Positive
    Integer limit;

    @PositiveOrZero
    Integer offset;

    public Integer getOffset() {
        if (offset == null) {
            offset = DEFAULT_OFFSET;
        }
        return offset;
    }
}