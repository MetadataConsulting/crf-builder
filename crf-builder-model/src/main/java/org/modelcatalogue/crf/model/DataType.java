package org.modelcatalogue.crf.model;

public enum DataType {
    /**
     *  Any characters can be provided for this data type.
     */
    ST,

    /**
     * Only numbers with no decimal places are allowed for this data type.
     */
    INT,

    /**
     *  Numbers with decimal places are allowed for this data type.
     */
    REAL,

    /**
     *  Only full dates are allowed for this data type.  The default date format the user must provide the value in is
     *  DD-MMM-YYYY.
     */
    DATE,

    /**
     * Partial dates are allowed for this data type.  The default date format is DD-MMM-YYYY so users can provide either
     * MMM-YYYY or YYYY values.
     */
    PDATE,

    /**
     * This data type allows files to be attached to the item.  It must be used in conjunction with a RESPONSE_TYPE
     * of file.  The attached file is saved to the server and a URL is displayed to the user viewing the form.
     */
    FILE

}
