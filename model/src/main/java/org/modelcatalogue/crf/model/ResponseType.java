package org.modelcatalogue.crf.model;

import java.util.Arrays;

public enum ResponseType {

    /**
     * Text is a rectangular box to enter information.
     */
    TEXT("text"),

    /**
     *  Textarea is a larger box where more information is visible to the person viewing the form with data.
     */
    TEXTAREA("textarea"),

    /**
     * Single-Select onlyS allow one option to be chosen for an item.
     */
    SINGLE_SELECT("single-select"),

    /**
     * Radio only allowS one option to be chosen for an item.
     *
     * Radio buttons cannot be deselected in the user interface once an option has been chosen.
     */
    RADIO("radio"),

    /**
     * Multi-Select allowS multiple options to be selected at once.
     */
    MULTI_SELECT("multi-select"),

    /**
     * Checkbox allowS multiple options to be selected at once.
     */
    CHECKBOX("checkbox"),

    /**
     * Calculation is used to derive values. Calculations allow for the execution of arithmetic expressions and support
     * some basic functions. The calculations use values from previous fields within the same CRF as variables.
     * The calculated field cannot be directly edited by the data entry person and will appear “grayed out”.
     *
     *  The values of calculated fields are generated when the user saves the section of the form.
     *
     * Forced reason for change (when turned on) is not enforced for calculated fields.
     */
    CALCULATION("calculation"),

    /**
     * Group-calculation is used to derive values. Group-calculation allow for the execution of arithmetic expressions
     * and support some basic functions. The calculations use values from previous fields within the same CRF as variables.
     * The calculated field cannot be directly edited by the data entry person and will appear “grayed out”.
     * Group-calculation allows the user to find a value based off of the column in a grid (e.g. sum).
     *
     * The group-calculation should not be contained in a repeating group, rather the variable that is being used
     * in the calculation should be in a grid while the calculated field itself is non-repeating.
     *
     * The values of calculated fields are generated when the user saves the section of the form.
     *
     * Forced reason for change (when turned on) is not enforced for calculated fields.
     */
    GROUP_CALCULATION("group-calculation"),

    /**
     * File allows a file to be uploaded and attached to the CRF by the data entry person.
     */
    FILE("file"),

    /**
     * Instant-calculation (introduced in 3.1.3) is used to populate a destination field with date/time information when
     * content of a parent field is changed. The trigger field must precede the instant-calculation field.  This is
     * a client side action; it is executed by triggering on change function defined by the CRF designer in the
     */
    INSTANT_CALCULATION("instant-calculation");

    private final String excelValue;

    ResponseType(String excelValue) {
        this.excelValue = excelValue;
    }

    public String getExcelValue() {
        return excelValue;
    }

    public boolean isSupportingDefaultValue() {
        return Arrays.asList(TEXT, TEXTAREA, SINGLE_SELECT, MULTI_SELECT, CHECKBOX).contains(this);
    }


}
