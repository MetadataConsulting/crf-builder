package org.modelcatalogue.crf.builder.util

import org.modelcatalogue.crf.builder.CaseReportFormExtensions
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.Section

/**
 * Base script for executing Case Report Form DSL scripts.
 */
abstract class CaseReportFormScript extends Script {

    /**
     * The current Case Report Form being generated.
     */
    @Delegate(deprecated = true) CaseReportForm delegate

    /**
     * Abstract method which will be replaced with script body.
     * @return
     */
    abstract void configure()

    /**
     * Runs the script, first delegating the binding variable form to the current delegate.
     * @return <code>null</code>
     */
    @Override Object run() {
        delegate = binding.form
        configure()
    }

    /**
     * Defines the name of the CRF as it will be displayed in the OpenClinica user interface.
     * When a user is assigning CRFs to an event definition, they will be viewing this name. A user performing data
     * entry will identify the form by this name.
     *
     * If the field is blank, the CRF will be rejected at upload time.
     *
     * @param name the name of the form which has to be unique alphanumeric string between 1 and 255 characters
     * @see CaseReportForm#name
     */
    void name(String name) {
        delegate.name = name
    }

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
     * @param version the version of the form which has to be unique alphanumeric string between 1 and 255 characters
     * @see CaseReportForm#version
     */
    void version(String version) {
        CaseReportFormExtensions.version(delegate, version)
    }

    /**
     * This field is used for informational purposes to keep track of what this version of the CRF was created for.
     *
     * This information appears as part of the CRF Metadata when the user clicks on View (original). This information
     * is not displayed during data entry.
     *
     * @param versionDescription the version description of the form which has to be alphanumeric string between 1 and 4000 characters
     * @see CaseReportForm#versionDescription
     */
    void versionDescription(String versionDescription) {
        CaseReportFormExtensions.versionDescription(delegate, versionDescription)
    }

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
     * @param revisionNotes the revision notes of the form which has to be alphanumeric string between 1 and 255 characters
     * @see CaseReportForm#revisionNotes
     */
    void revisionNotes(String revisionNotes) {
        CaseReportFormExtensions.revisionNotes(delegate, revisionNotes)
    }

    /**
     * Creates new section with given label in current form.
     *
     * Calling with the same label will cause reconfiguring already existing section.
     *
     * @param label unique label of the section which can contain 1 to 255 alphanumeric characters and dashes
     * @param closure configuration closure using section as a delegate
     * @return new or existing section with given label
     */
    Section section(String label, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Section) Closure closure) {
        CaseReportFormExtensions.section(delegate, label, closure)
    }

}
