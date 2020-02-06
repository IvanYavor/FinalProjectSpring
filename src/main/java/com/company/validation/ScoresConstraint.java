package com.company.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=ScoresConstraintValidator.class)
public @interface ScoresConstraint {
    String message() default "Invalid score";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}