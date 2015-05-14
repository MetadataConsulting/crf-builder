package org.modelcatalogue.crf.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.*;

import static org.modelcatalogue.crf.model.validation.ValidationConstants.ALPHA_NUMERIC_NO_SPACES_PATTERN;

public class Group {

    Group() {}

    /**
     * Must be unique in the worksheet and contain no spaces. GROUP_LABEL is referenced in the Items Worksheet to
     * associate items with the appropriate item group in the CRF. The GROUP_LABEL value will be used to generate the
     * GROUP OID to be used in Rules, Data Import, and Export.
     *
     * The field is not displayed as part of the CRF but can be seen on the CRF Metadata page.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_NO_SPACES_PATTERN) private String label;

    /**
     * Used in conjunction with Dynamics in Rules. If set to HIDE, the item group will not appear in the CRF when a user
     * is entering data until certain criteria are met. This criteria is specified with a Rule and using the ShowAction.
     *
     * If left blank, the value defaults to SHOW.
     */
    @NotNull private DisplayStatus displayStatus;

    @NotNull @Valid private Section section;

    private Map<String, Item> items = new LinkedHashMap<String, Item>();

    public Item item(String name) {
        Item item = new Item();
        item.setName(name);
        addItem(item);
        return item;
    }

    Item addItem(Item item) {
        if (!items.containsKey(item.getName())) {
            items.put(item.getName(), item);
            item.setGroup(this);
            item.setSection(this.getSection());
            this.getSection().addItem(item);
        }
        return item;
    }

    public Map<String, Item> getItems() {
        return Collections.unmodifiableMap(items);
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
