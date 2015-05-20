package org.modelcatalogue.crf.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.*;

import static org.modelcatalogue.crf.model.validation.ValidationConstants.ALPHA_NUMERIC_PATTERN;

public class CaseReportForm {

    /**
     * Defines the name of the CRF as it will be displayed in the OpenClinica user interface.
     * When a user is assigning CRFs to an event definition, they will be viewing this name. A user performing data
     * entry will identify the form by this name.
     *
     * If the field is blank, the CRF will be rejected at upload time.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String name;

    /**
     * Defines the version of the CRF as it will be displayed in the OpenClinica user interface.
     *
     * You cannot provide a value that has already been used in the OpenClinica instance unless it has not been assigned
     * to an event definition yet.  If a particular CRF version has not been used in an event definition, you may
     * overwrite it.
     *
     * If this is a new version of a CRF that already exists, the CRF_NAME field must match the value of the form
     * already in OpenClinica.
     *
     * A new version of a CRF would be needed due to a protocol change, adding or removing an item from a CRF, or
     * changing some of the questions.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String version;


    /**
     * This field is used for informational purposes to keep track of what this version of the CRF was created for.
     *
     * This information appears as part of the CRF Metadata when the user clicks on View (original). This information
     * is not displayed during data entry.
     */
    @NotNull @Size(min = 1, max = 4000) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String versionDescription;

    /**
     * This field is used to keep track of the revisions you made to this particular CRF.
     *
     * This information appears as part of the CRF Metadata when the user clicks on View (original). This information is
     * not displayed during data entry.
     *
     * If this is the first version of the CRF, you can simply state this is a brand new form.  Going forward, as you
     * make changes and update the versions you can provide information on what is different between the first version
     * and each subsequent version.
     *
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String revisionNotes;


    @Size(min = 1) private Map<String, Section> sections = new LinkedHashMap<String, Section>();

    public Section section(String label) {
        if (sections.containsKey(label)) {
            return sections.get(label);
        }
        Section section = new Section();
        section.setLabel(label);
        addSection(section);
        return section;
    }

    private Section addSection(Section section) {
        if (!sections.containsKey(section.getLabel())) {
            sections.put(section.getLabel(), section);
            section.setCaseReportForm(this);
        }
        return section;
    }

    public Map<String, Section> getSections() {
        return Collections.unmodifiableMap(sections);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public String getRevisionNotes() {
        return revisionNotes;
    }

    public void setRevisionNotes(String revisionNotes) {
        this.revisionNotes = revisionNotes;
    }

    public Item findItem(String name) {
        for (Section section : sections.values()) {
            Item found = section.items.get(name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
