package org.modelcatalogue.crf.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.*;

import static org.modelcatalogue.crf.model.validation.ValidationConstants.ALPHA_NUMERIC_PATTERN;

public class CaseReportForm {

    // TODO: add validation of unique items such as section labels, group labels and item names

    /**
     * Defines the name of the CRF as it will be displayed in the OpenClinica user interface.
     * When a user is assigning CRFs to an event definition, they will be viewing this name. A user performing data
     * entry will identify the form by this name.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String name;

    /**
     * Defines the version of the CRF as it will be displayed in the OpenClinica user interface.
     *
     * You cannot provide a value that has already been used in the OpenClinica instance unless it has not been assigned
     * to an event definition yet.  If a particular CRF version has not been used in an event definition, you may
     * overwrite it.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String version;


    /**
     * This field is used for informational purposes to keep track of what this version of the CRF was created for.
     */
    @NotNull @Size(min = 1, max = 4000) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String versionDescription;

    /**
     * This field is used to keep track of the revisions you made to this particular CRF.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_PATTERN) private String revisionNotes;


    private Map<String, Section> sections = new LinkedHashMap<String, Section>();

    public Section section(String label) {
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
}
