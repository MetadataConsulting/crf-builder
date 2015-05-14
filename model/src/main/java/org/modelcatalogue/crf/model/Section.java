package org.modelcatalogue.crf.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.*;

import static org.modelcatalogue.crf.model.validation.ValidationConstants.ALPHA_NUMERIC_NO_SPACES_PATTERN;

public class Section {

    Section() {}

    /**
     * Must be unique in the worksheet and contain no spaces.
     *
     * The SECTION_LABEL is referenced in the Items Worksheet to associate items with the appropriate section of the CRF.
     * When the CRF is accessed for data entry, each section will be a page.  The items defined with the corresponding
     * SECTION_LABEL will be shown on that single page.
     *
     * This field is not displayed as part of the CRF but can be seen on the CRF Metadata page.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_NO_SPACES_PATTERN) private String label;

    /**
     * The value in this field will be displayed at the top of each page when a user is performing data entry, as well
     * as in the tabs and drop down list used to navigate between sections in a CRF. It does not have to be unique but
     * should be a readable value that makes sense to people entering data.  An example would be 'Inclusion Criteria'
     */
    @NotNull @Size(min = 1, max = 2000) private String title;


    /**
     * A sub-title shown under the section title.
     */
    @Size(max = 2000) private String subtitle;

    /**
     * Instructions at the top of the section (under the subtitle) that explains to the data entry person what to do on
     * this section of the form.
     */
    @Size(max = 2000) private String instructions;

    /**
     * The page number on which the section begins. If using paper source documents and have a multi-page CRF,
     * put in the printed page number.
     */
    @Size(max = 5) private String pageNumber;

    private CaseReportForm caseReportForm;

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
            item.setSection(this);
        }
        return item;
    }

    public Map<String, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    private Map<String, Group> groups = new LinkedHashMap<String, Group>();

    /**
     * Creates new non-repeating group with given label.
     * @param label label of the new non-repeating group.
     * @return new non-repeating group with given label.
     */
    public Group group(String label) {
        Group group = new Group();
        group.setLabel(label);
        addGroup(group);
        return group;
    }

    /**
     * Creates new repeating grid group with given label.
     * @param label label of the new repeating grid group.
     * @return new repeating grid group with given label.
     */
    public GridGroup gridGroup(String label) {
        GridGroup group = new GridGroup();
        group.setLabel(label);
        addGroup(group);
        return group;
    }

    private Group addGroup(Group group) {
        if (!groups.containsKey(group.getLabel())) {
            groups.put(group.getLabel(), group);
            group.setSection(this);
        }
        return group;
    }

    public Map<String, Group> getGroups() {
        return Collections.unmodifiableMap(groups);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public CaseReportForm getCaseReportForm() {
        return caseReportForm;
    }

    void setCaseReportForm(CaseReportForm caseReportForm) {
        this.caseReportForm = caseReportForm;
    }
}
