package org.modelcatalogue.crf.model.validation;

import org.modelcatalogue.crf.model.GridGroup;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRepeatConstraint implements ConstraintValidator<ValidRepeat, GridGroup> {

    @Override public void initialize(ValidRepeat constraintAnnotation) {}

    @Override
    public boolean isValid(GridGroup value, ConstraintValidatorContext context) {
        return value.getRepeatNum() == null || value.getRepeatMax() == null ||  value.getRepeatMax() >= value.getRepeatNum();
    }
}
