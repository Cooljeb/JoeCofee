package com.joe.coffee.api.Utils.YearConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface YearConstraint {

    String message() default "L'année doit être valide et ne pas être dans le futur";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 1800;
}
