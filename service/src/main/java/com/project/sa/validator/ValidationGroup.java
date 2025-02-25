package com.project.sa.validator;

import javax.validation.groups.Default;

public class ValidationGroup {
    public interface CreateValidation extends Default {
    }

    public interface PutValidation extends Default {
    }

    public interface PatchValidation extends Default {
    }
}
