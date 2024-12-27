package fci.swe.advanced_software.utils.validators.internal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UlidValidator implements ConstraintValidator<ULID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || com.github.f4b6a3.ulid.Ulid.isValid(value);
    }
}
