package org.modelcatalogue.crf.model

import org.modelcatalogue.crf.model.validation.ValidationHelper
import org.modelcatalogue.crf.model.validation.ValidationExpression
import org.springframework.validation.Errors
import spock.lang.Specification
import spock.lang.Unroll

class ItemSpec extends Specification {

    @Unroll
    def "parse #string to response options #options"() {
        expect:
        options == Item.parseResponseOptions(string)

        where:
        string      | options
        'a,b,c'     | ['a','b','c']
        'a\\\\,b,c' | ['a,b','c']
    }

    @Unroll
    def "store #options as #string"() {
        expect:
        string == Item.storeResponseOptions(options)

        where:
        string      | options
        'a,b,c'     | ['a','b','c']
        'a\\\\,b,c' | ['a,b','c']

    }

    def "store response options"() {
        Item item = new Item(ResponseType.SINGLE_SELECT)
        item.responseOptions = [
                new ResponseOption('Male', 'm'),
                new ResponseOption('Female', 'f')
        ]

        expect:
        item.responseOptionsText == 'Male,Female'
        item.responseValuesOrCalculations == 'm,f'
    }

    def "test supported value failed"() {
        Errors errors = ValidationHelper.validate(Item, defaultValue: "Foo", responseType: ResponseType.FILE)

        expect:
        errors.globalError?.code == 'ValidDefaultValue'
    }

    def "test supported value passes"() {
        Errors errors = ValidationHelper.validate(Item, defaultValue: "Foo", responseType: ResponseType.TEXT)

        expect:
        errors.globalError?.code != 'ValidDefaultValue'
    }


    def "test missing validation message"() {
        Errors errors = ValidationHelper.validate(Item, validationExpression: new ValidationExpression("gt(1)", null))

        expect:
        errors.globalError?.code == 'ValidValidationMessage'
    }

    def "test present validation message"() {
        Errors errors = ValidationHelper.validate(Item, validationExpression: new ValidationExpression("gt(1)",  'Must be greater 1.'))

        expect:
        errors.globalError?.code != 'ValidValidationMessage'
    }


    def "test file data and response type must be coherent"() {
        Errors errors = ValidationHelper.validate(Item, dataType: DataType.INT, responseType: ResponseType.FILE)

        expect:
        errors.globalError?.code == 'ValidResponseType'
    }

    def "test file data and response type should be both file"() {
        Errors errors = ValidationHelper.validate(Item, dataType: DataType.FILE, responseType: ResponseType.FILE)

        expect:
        errors.globalError?.code != 'ValidResponseType'
    }

    @Unroll
    def "for data type #type the width decimal #width value valid=#valid"() {
        Errors errors = ValidationHelper.validate(Item, widthDecimal: width, dataType: type)

        expect:
        valid != (errors.globalError?.code == 'ValidWidthDecimal')

        where:
        width     | valid | type
        'w(d)'    | true  | DataType.INT
        '4(d)'    | true  | DataType.INT
        '27(d)'   | false | DataType.INT
        '4(2)'    | true  | DataType.REAL
        '2(4)'    | false | DataType.REAL
        '4(d)'    | true  | DataType.REAL
        'w(d)'    | true  | DataType.REAL
        'w(d)'    | true  | DataType.ST
        'w(5)'    | false | DataType.ST
        '5(d)'    | true  | DataType.ST
        '4001(d)' | false | DataType.ST
    }



}
