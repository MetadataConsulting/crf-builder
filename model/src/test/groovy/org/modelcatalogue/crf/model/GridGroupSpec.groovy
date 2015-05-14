package org.modelcatalogue.crf.model

import org.modelcatalogue.crf.model.validation.Validation
import org.springframework.validation.Errors
import spock.lang.Specification

class GridGroupSpec extends Specification {

    def "test valid repeat constraint fails"() {
        Errors errors = Validation.validate(GridGroup, repeatNum: 20, repeatMax: 15)

        expect:
        errors.globalError.code == 'ValidRepeat'
    }

    def "test valid repeat constraint passes"() {
        Errors errors = Validation.validate(GridGroup, repeatNum: 20, repeatMax: 25)

        expect:
        errors.globalError?.code != 'ValidRepeat'
    }

}
