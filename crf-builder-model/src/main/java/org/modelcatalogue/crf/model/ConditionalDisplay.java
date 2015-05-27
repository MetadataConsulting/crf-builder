package org.modelcatalogue.crf.model;

public class ConditionalDisplay {
    private final ResponseOption response;
    private final String message;

    public ConditionalDisplay(ResponseOption response, String message) {
        this.response = response;
        this.message = message;
    }

    public ResponseOption getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }
}
