package org.modelcatalogue.crf.model.validation;

public class ValidationConstants {

    public static final String ALPHA_NUMERIC_PATTERN = "^[\\pL\\pN _\\-\\.]*$";
    public static final String ALPHA_NUMERIC_NO_SPACES_PATTERN =  "^[\\pL\\pN_\\-\\.]*$";
    public static final String ALPHA_NUMERIC_ENGLISH_NO_SPACES_PATTERN =  "^[a-zA-Z0-9_\\-\\.]*$";
    public static final String WIDTH_DECIMAL_PATTERN = "[w(\\d{1,4})]\\([d(\\d{1,2})]\\)";

    private ValidationConstants(){}

}
