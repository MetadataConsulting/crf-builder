package org.modelcatalogue.crf.model.validation;

import org.modelcatalogue.crf.model.Item;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidValidationMessageConstraint implements ConstraintValidator<ValidValidationMessage, Item> {

    @Override public void initialize(ValidValidationMessage constraintAnnotation) {}

    @Override
    public boolean isValid(Item value, ConstraintValidatorContext context) {
        return value.getValidation() == null || value.getValidationErrorMessage() != null;
    }
}
