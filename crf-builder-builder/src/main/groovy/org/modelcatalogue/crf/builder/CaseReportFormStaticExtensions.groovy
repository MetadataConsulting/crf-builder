package org.modelcatalogue.crf.builder

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.modelcatalogue.crf.builder.util.CaseReportFormScript
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.DataType
import org.modelcatalogue.crf.model.GenericItem
import org.modelcatalogue.crf.model.HasResponseLayout
import org.modelcatalogue.crf.model.ResponseLayout

class CaseReportFormStaticExtensions {

    // form builder
    /**
     * Builds the new form using the form builder DSL.
     *
     * @param ignored just for meeting Extension Modules definitions
     * @param name name of the form which has to be unique alphanumeric string between 1 and 255 characters
     * @param closure configuration dsl closure which is executed in context of newly created form
     * @return form of given name
     */
    static CaseReportForm build(CaseReportForm ignored, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=CaseReportForm) Closure closure) {
        CaseReportForm form = new CaseReportForm(name: name)
        form.with closure
        form
    }

    // data types shortcuts
    /**
     *  Any characters can be provided for this data type.
     */
    static DataType getString(GenericItem ignored) {
        DataType.ST
    }

    /**
     * Only numbers with no decimal places are allowed for this data type.
     */
    static DataType getInteger(GenericItem ignored) {
        DataType.INT
    }

    /**
     *  Numbers with decimal places are allowed for this data type.
     */
    static DataType getReal(GenericItem ignored) {
        DataType.REAL
    }

    /**
     *  Only full dates are allowed for this data type.  The default date format the user must provide the value in is
     *  DD-MMM-YYYY.
     */
    static DataType getDate(GenericItem ignored) {
        DataType.DATE
    }

    /**
     * Partial dates are allowed for this data type.  The default date format is DD-MMM-YYYY so users can provide either
     * MMM-YYYY or YYYY values.
     */
    static DataType getPartialDate(GenericItem ignored) {
        DataType.PDATE
    }

    /**
     * Vertical layout.
     */
    static ResponseLayout getVertical(HasResponseLayout ignored) {
        ResponseLayout.VERTICAL
    }

    /**
     * Horizontal layout.
     */
    static ResponseLayout getHorizontal(HasResponseLayout ignored) {
        ResponseLayout.HORIZONTAL
    }

    /**
     * Loads form from the DSL file.
     * @param file DSL file
     */
    static CaseReportForm load(CaseReportForm ignored, File file) {
        importDslFile(file.newInputStream())
    }


    private static CaseReportForm importDslFile(InputStream inputStream) {
        CaseReportForm form = new CaseReportForm()
        GroovyShell shell = prepareGroovyShell(form)
        shell.evaluate(inputStream.newReader())
        form
    }

    private static GroovyShell prepareGroovyShell(CaseReportForm form) {
        CompilerConfiguration configuration = new CompilerConfiguration()
        configuration.scriptBaseClass = CaseReportFormScript.name

        SecureASTCustomizer secureASTCustomizer = new SecureASTCustomizer()
        secureASTCustomizer.with {
            packageAllowed = false
            indirectImportCheckEnabled = true

            importsWhitelist = [Object.name, CaseReportForm.name, CaseReportFormExtensions.name, CaseReportFormStaticExtensions.name]
            starImportsWhitelist = [Object.name, CaseReportForm.name, CaseReportFormExtensions.name, CaseReportFormStaticExtensions.name]
            staticImportsWhitelist = [Object.name, CaseReportForm.name, CaseReportFormExtensions.name, CaseReportFormStaticExtensions.name]
            staticStarImportsWhitelist = [Object.name, CaseReportForm.name, CaseReportFormExtensions.name, CaseReportFormStaticExtensions.name]

            receiversClassesBlackList = [System]
        }
        configuration.addCompilationCustomizers secureASTCustomizer

        new GroovyShell(new Binding(form: form), configuration)
    }
}
