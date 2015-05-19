package org.modelcatalogue.crf.builder.util

import org.modelcatalogue.crf.builder.CaseReportFormExtensions
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.Section

abstract class CaseReportFormScript extends Script {

    @Delegate(deprecated = true) CaseReportForm delegate

    abstract configure()

    @Override Object run() {
        delegate = binding.form
        configure()
    }

    void name(String name) {
        delegate.name = name
    }

    void version(String version) {
        CaseReportFormExtensions.version(delegate, version)
    }

    void versionDescription(String versionDescription) {
        CaseReportFormExtensions.versionDescription(delegate, versionDescription)
    }

    void revisionNotes(String revisionNotes) {
        CaseReportFormExtensions.revisionNotes(delegate, revisionNotes)
    }

    Section section(String label, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Section) Closure closure) {
        CaseReportFormExtensions.section(delegate, label, closure)
    }

}
