package com.seek.cliente.interfaces;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PastDateValidator  implements ConstraintValidator<PastDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // La validación de null se maneja con @NotNull
        }
        return value.isBefore(LocalDate.now());
    }

}
