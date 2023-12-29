package br.edu.ifpe.tads.imdb.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidatorNota implements ConstraintValidator<ValidaNota, Double> {
    @Override
    public void initialize(ValidaNota constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= 0.0 && value <= 5.0;
    }
}
