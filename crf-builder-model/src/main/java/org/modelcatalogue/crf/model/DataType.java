package org.modelcatalogue.crf.model;

public enum DataType {
    /**
     *  Any characters can be provided for this data type.
     */
    ST("String"),

    /**
     * Only numbers with no decimal places are allowed for this data type.
     */
    INT("Integer"),

    /**
     *  Numbers with decimal places are allowed for this data type.
     */
    REAL("Real"),

    /**
     *  Only full dates are allowed for this data type.  The default date format the user must provide the value in is
     *  DD-MMM-YYYY.
     */
    DATE("Date"),

    /**
     * Partial dates are allowed for this data type.  The default date format is DD-MMM-YYYY so users can provide either
     * MMM-YYYY or YYYY values.
     */
    PDATE("Partial Date"),

    /**
     * This data type allows files to be attached to the item.  It must be used in conjunction with a RESPONSE_TYPE
     * of file.  The attached file is saved to the server and a URL is displayed to the user viewing the form.
     */
    FILE("File");

    private final String humanReadable;

    DataType(String humanReadable) {
        this.humanReadable = humanReadable;
    }

    public String getHumanReadable() {
        return humanReadable;
    }
}
