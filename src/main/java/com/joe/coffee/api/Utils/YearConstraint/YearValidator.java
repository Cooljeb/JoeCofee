package com.joe.coffee.api.Utils.YearConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearValidator implements ConstraintValidator<YearConstraint, String> {
    private int minYear;

    @Override
    public void initialize(YearConstraint constraintAnnotation) {
        this.minYear = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        if (!value.matches("\\d{4}")) {
            return false;
        }

        int year = Integer.parseInt(value);
        int currentYear = Year.now().getValue();

        return year >= minYear && year <= currentYear;
    }
}
