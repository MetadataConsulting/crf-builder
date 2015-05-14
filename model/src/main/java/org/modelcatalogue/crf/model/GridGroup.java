package org.modelcatalogue.crf.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GridGroup extends Group {

    public GridGroup() {}

    // TODO: validate tha the repeatNum is always lower or equal to repeatMax
    // TODO: validate that all the items in the group has the same section

    /**
     * The value is displayed above the GRID when a user is performing data entry.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     */
    @NotNull @Size(min = 1, max = 255) private String header;

    /**
     * The default (initial) number of repeats on the form should be provided here. If left blank, only one row of
     * information will be displayed by default.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     */
    @Min(1) private Integer repeatNum;


    /**
     * The total number of rows a user will be allowed to repeat in the item group.  When left blank, the default number
     * of repeats is 40.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
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
