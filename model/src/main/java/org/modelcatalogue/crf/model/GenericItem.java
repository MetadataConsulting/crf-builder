package org.modelcatalogue.crf.model;

import org.modelcatalogue.crf.model.validation.ValidationExpression;

public interface GenericItem extends MinimalItem {

    String getWidthDecimal();
    void setWidthDecimal(String widthDecimal);

    ValidationExpression getValidationExpression();
    void setValidationExpression(ValidationExpression expression);

    DataType getDataType();
    void setDataType(DataType dataType);

}
