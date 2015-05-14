package org.modelcatalogue.crf.model

import org.modelcatalogue.crf.model.validation.Validation
import org.springframework.validation.Errors
import spock.lang.Specification

class CaseReportFormSpec extends Specification {

    def "validate form"() {
        Errors results = Validation.validate(CaseReportForm, name: 'Test Form', version: '')

        expect:
        results.getFieldError('version')?.code == 'Size'
        results.getFieldError('versionDescription')?.code == 'NotNull'
        results.getFieldError('revisionNotes')?.code == 'NotNull'

    }

}
