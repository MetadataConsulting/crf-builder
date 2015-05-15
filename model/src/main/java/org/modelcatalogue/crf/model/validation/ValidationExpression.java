package org.modelcatalogue.crf.model.validation;

public class ValidationExpression {
    private final String expression;
    private final String message;

    public ValidationExpression(String expression, String message) {
        this.expression = expression;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getExpression() {
        return expression;
    }
}
