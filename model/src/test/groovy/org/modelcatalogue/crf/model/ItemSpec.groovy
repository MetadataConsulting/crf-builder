package org.modelcatalogue.crf.model

import spock.lang.Specification
import spock.lang.Unroll

class ItemSpec extends Specification {

    @Unroll
    def "parse #string to response options #options"() {
        expect:
        options == Item.parseResponseOptions(string)

        where:
        string  | options
        'a,b,c' | ['a','b','c']
        'a/,b,c'| ['a,b','c']
    }

    @Unroll
    def "store #options as #string"() {
        expect:
        string == Item.storeResponseOptions(options)

        where:
        string  | options
        'a,b,c' | ['a','b','c']
        'a/,b,c'| ['a,b','c']

    }

    def "store response options"() {
        Item item = new Item()
        item.responseOptions = [
                new ResponseOption('Male', 'm'),
                new ResponseOption('Female', 'f')
        ]

        expect:
        item.responseOptionsText == 'Male,Female'
        item.responseValuesOrCalculations == 'm,f'
    }

}
