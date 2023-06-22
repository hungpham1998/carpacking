package com.carparking.core_utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class DuplicateConstraintValidator
    implements ConstraintValidator<NoDuplicate, List<?>> {

  @Override
  public boolean isValid(List value, ConstraintValidatorContext context) {
    if (Objects.isNull(value) || value.isEmpty()) return true;
    return new HashSet<>(value).size() == value.size();
  }
}
