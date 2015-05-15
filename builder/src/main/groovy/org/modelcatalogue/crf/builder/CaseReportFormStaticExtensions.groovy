package org.modelcatalogue.crf.builder

import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.DataType
import org.modelcatalogue.crf.model.GenericItem
import org.modelcatalogue.crf.model.HasResponseLayout
import org.modelcatalogue.crf.model.ResponseLayout

class CaseReportFormStaticExtensions {

    // form builder
    static CaseReportForm build(CaseReportForm ignored, String name, @DelegatesTo(CaseReportForm) Closure closure) {
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
}
