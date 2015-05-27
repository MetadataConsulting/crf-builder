package org.modelcatalogue.crf.model.validation

import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ResponseType
import org.springframework.beans.MutablePropertyValues
import org.springframework.validation.DataBinder
import org.springframework.validation.Errors
import org.springframework.validation.beanvalidation.SpringValidatorAdapter

import javax.validation.Validation
import javax.validation.Validator

class ValidationHelper {

    private ValidationHelper(){}

    static Errors validate(Map<String, Object> properties = [:], Class<?> type) {

        def target = type == Item ? type.newInstance(properties.responseType ?: ResponseType.TEXT) : type.newInstance()

        DataBinder initializer = new DataBinder(target)
        initializer.bind(new MutablePropertyValues(properties))

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        SpringValidatorAdapter validatorAdapter = new SpringValidatorAdapter(validator);

        DataBinder binder = new DataBinder(target);
        binder.addValidators(validatorAdapter);
        binder.validate();
        binder.getBindingResult();
    }

}
