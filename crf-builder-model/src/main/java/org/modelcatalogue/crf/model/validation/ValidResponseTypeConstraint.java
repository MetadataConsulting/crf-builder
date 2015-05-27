package org.modelcatalogue.crf.model.validation;

import org.modelcatalogue.crf.model.DataType;
import org.modelcatalogue.crf.model.Item;
import org.modelcatalogue.crf.model.ResponseType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidResponseTypeConstraint implements ConstraintValidator<ValidResponseType, Item> {

    @Override public void initialize(ValidResponseType constraintAnnotation) {}

    @Override
    public boolean isValid(Item value, ConstraintValidatorContext context) {
        return value.getDataType() == null || value.getResponseType() == null || value.getDataType() == DataType.FILE && value.getResponseType() == ResponseType.FILE || value.getDataType() != DataType.FILE && value.getResponseType() != ResponseType.FILE;
    }
}
