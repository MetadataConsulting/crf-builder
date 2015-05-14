package org.modelcatalogue.crf.model.validation;

import org.modelcatalogue.crf.model.Item;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDefaultValueConstraint implements ConstraintValidator<ValidDefaultValue, Item> {

    @Override public void initialize(ValidDefaultValue constraintAnnotation) {}

    @Override
    public boolean isValid(Item value, ConstraintValidatorContext context) {
        return value.getResponseType() == null || value.getDefaultValue() == null || value.getResponseType().isSupportingDefaultValue();
    }
}
