package org.modelcatalogue.crf.model;

import org.modelcatalogue.crf.model.validation.ValidRepeat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValidRepeat
public class GridGroup extends Group {

    GridGroup() {}

    /**
     * The value is displayed above the GRID when a user is performing data entry.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     *
     * Only use when the layout is set to GRID.  This value is like a title for the group.  An example of a GROUP_HEADER
     * would be "Medications Log."
     *
     * The field can be left blank if you do not want a title or header.  If the Layout is set to NON-REPEATING,
     * the value will be ignored and not displayed during data entry.
     */
    @NotNull @Size(min = 1, max = 255) private String header;

    /**
     * The default (initial) number of repeats on the form should be provided here. If left blank, only one row of
     * information will be displayed by default.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     *
     * This field should be used to specify how many rows of data should exist for the item group upon initiation of
     * data entry, or in printing of a blank CRF from OpenClinica. If three rows of information, specify the number 3
     * in the field. When a user accesses the CRF, the row will be repeated 3 times by default.
     *
     * A user will be allowed to add more rows up to the GROUP_REPEAT_MAX and even remove some of the rows displayed
     * by default.
     */
    @Min(1) private Integer repeatNum;


    /**
     * The total number of rows a user will be allowed to repeat in the item group.  When left blank, the default number
     * of repeats is 40.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     *
     * This field should be used to restrict users to a certain number of repeats for the GRID.  However, this
     * restriction works only if data are entered through OpenClinica Web UI. If data are imported using
     * Task-> Import Data option or through web services, all rows of data in the import file will be allowed to import,
     * even if the rows of data in the import exceed the GROUP_REPEAT_MAX..
     *
     * If GROUP_REPEAT_MAX is less than GROUP_REPEAT_NUMBER group will have GROUP_REPEAT_MAX number of rows on initial
     * data entry displayed and no additional rows can be added.
     */
    @Min(1) private Integer repeatMax;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getRepeatNum() {
        return repeatNum;
    }

    public void setRepeatNum(Integer repeatNum) {
        this.repeatNum = repeatNum;
    }

    public Integer getRepeatMax() {
        return repeatMax;
    }

    public void setRepeatMax(Integer repeatMax) {
        this.repeatMax = repeatMax;
    }
}
