package com.carparking.core_utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DuplicateConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
public @interface NoDuplicate {
  String message() default "com.carparking.validation.NoDuplicate";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
