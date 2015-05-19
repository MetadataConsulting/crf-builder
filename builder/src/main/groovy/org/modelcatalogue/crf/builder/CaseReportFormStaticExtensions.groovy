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
    static CaseReportForm build(CaseReportForm ignored, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=CaseReportForm) Closure closure) {
        CaseReportForm form = new CaseReportForm(name: name)
        form.with closure
        form
    }

    // data types shortcuts
    static DataType getString(GenericItem ignored) {
        DataType.ST
    }

    static DataType getInteger(GenericItem ignored) {
        DataType.INT
    }

    static DataType getReal(GenericItem ignored) {
        DataType.REAL
    }

    static DataType getDate(GenericItem ignored) {
        DataType.DATE
    }

    static DataType getPartialDate(GenericItem ignored) {
        DataType.PDATE
    }

    static ResponseLayout getVertical(HasResponseLayout ignored) {
        ResponseLayout.VERTICAL
    }

    static ResponseLayout getHorizontal(HasResponseLayout ignored) {
        ResponseLayout.HORIZONTAL
    }

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
