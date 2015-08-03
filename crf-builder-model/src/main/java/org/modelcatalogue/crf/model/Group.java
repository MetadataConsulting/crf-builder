package org.modelcatalogue.crf.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static org.modelcatalogue.crf.model.validation.ValidationConstants.ALPHA_NUMERIC_NO_SPACES_PATTERN;

public class Group extends AbstractItemsContainer implements HasDisplayStatus {

    Group() {}

    /**
     * Must be unique in the worksheet and contain no spaces. GROUP_LABEL is referenced in the Items Worksheet to
     * associate items with the appropriate item group in the CRF. The GROUP_LABEL value will be used to generate the
     * GROUP OID to be used in Rules, Data Import, and Export.
     *
     * The field is not displayed as part of the CRF but can be seen on the CRF Metadata page.
     *
     * This field should be used in order to logically group items together. Item groups may be used to define repeating
     * items in the CRF, or for logical grouping of non-repeating items for easier data analysis once data has been
     * exported from OpenClinica.  The entire GROUPS worksheet can be left blank if desired and all the items in the CRF
     * can be part of a single group called UNGROUPED.
     *
     * We suggest providing records in this worksheet only if you are going to use Groups for Items grouping on Items
     * worksheet.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_NO_SPACES_PATTERN) private String label;

    /**
     * Used in conjunction with Dynamics in Rules. If set to HIDE, the item group will not appear in the CRF when a user
     * is entering data until certain criteria are met. This criteria is specified with a Rule and using the ShowAction.
     *
     * If left blank, the value defaults to SHOW.
     *
     * If you would like to design skip patterns and dynamic logic for the item groups, set the display status to HIDE.
     * When the form is accessed for data entry, the group of items will be hidden from view from the user.  With Rules,
     * entry of a desired value elsewhere in data entry can trigger the item group  to then be shown instead of hidden
     * (this applies to GRID and NON-REPEATING). The opposite is true as well.  Groups that are set to HIDE which have
     * been shown as a result of a rule can also be hidden based on certain criteria being met.
     *
     * Note that the GROUP is the container for items and that items within the group can have nested SHOW/HIDE logic as
     * well. If a hidden group is shown but some of the items within that group have been set to HIDE within the ITEMS
     * worksheet, additional logic will have to be built for those individual items. If all items within the group are
     * set to SHOW within the items worksheet, then showing the time group will show all items.
     */
    @NotNull private DisplayStatus displayStatus;

    /**
     * As all items must belong to same section it make sense to keep the section refrence from the group instead from
     * the items.
     */
    @NotNull @Valid private Section section;

    protected Item addItem(Item item) {
        if (!items.containsKey(item.getName())) {
            items.put(item.getName(), item);
            item.setGroup(this);
            item.setSection(this.getSection());
            this.getSection().addItem(item);
        }
        return item;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DisplayStatus getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(DisplayStatus displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Section getSection() {
        return section;
    }

    void setSection(Section section) {
        this.section = section;
    }
}
