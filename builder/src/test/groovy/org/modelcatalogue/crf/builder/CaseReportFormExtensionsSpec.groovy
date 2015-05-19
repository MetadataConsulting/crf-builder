package org.modelcatalogue.crf.builder

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.DataType
import org.modelcatalogue.crf.model.DisplayStatus
import org.modelcatalogue.crf.model.GridGroup
import org.modelcatalogue.crf.model.Group
import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ResponseLayout
import org.modelcatalogue.crf.model.ResponseType
import org.modelcatalogue.crf.model.Section
import org.modelcatalogue.crf.model.Text
import spock.lang.Specification
import spock.lang.Unroll
import java.sql.Date as SqlDate

class CaseReportFormExtensionsSpec extends Specification {


    public static final String FORM_NAME = 'TestForm'
    public static final String VERSION = 'v1.1'
    public static final String VERSION_DESCRIPTION = 'next minor version'
    public static final String REVISION_NOTES = 'This was created for test purposes.'
    public static final String SECTION_LABEL = 'TheSection'
    public static final String SECTION_TITLE = 'This is a title of the section'
    public static final String SECTION_SUBTITLE = 'This is a subtitle of the section'
    public static final String SECTION_INSTRUCTIONS = 'These are instructions of the section'
    public static final String SECTION_PAGE_NUMBER = '5'
    public static final String GROUP_LABEL = "THE GROUP"
    public static final String GRID_LABEL = "THE GRID"
    public static final String GRID_HEADER = "THE GRID HEADER"
    public static final int GRID_REPEAT_NUM = 10
    public static final int GRID_REPEAT_MAX = 30
    public static final String TEXT_NAME = "text_item_name"
    public static final String TEXT_DESCRIPTION = "This is a TEXT Item"
    public static final String TEXT_ITEM_LEFT = "This is a TEXT Item LEFT"
    public static final String TEXT_UNITS = "This is a TEXT Item UNITS"
    public static final String TEXT_ITEM_RIGHT = "This is a TEXT Item RIGHT"
    public static final String TEXT_ITEM_HEADER = "This is a TEXT Item HEADER"
    public static final String TEXT_ITEM_SUBHEADER = "This is a TEXT Item SUBHEADER"
    public static final String TEXT_ITEM_QUESTION_NUMBER = "90B"
    public static final String TEXT_WIDTH_DECIMAL = "50(d)"
    public static final String REGEXP_VALIDATION = "regexp: /[A-Z]{3}/"
    public static final String REGEXP_ERROR = "Value should be "
    public static final String ROW_1_NAME = "R1"
    public static final String ROW_2_NAME = "R2"
    public static final String ROW_3_NAME = "R3"
    public static final DataType TEXT_ITEM_DATA_TYPE = DataType.PDATE
    public static final String CHECKBOX_NAME = "check_b_o_x"
    public static final String RADIO_NAME = "radio_name"
    public static final int RADIO_MAX = 20
    public static final String RADIO_VALIDATION = "func: lte(20)"
    public static final String RADIO_VALIDATION_MESSAGE = "Value must be lower than or equal to 20."
    public static final String CHECKBOX_VALUE = "checked"
    public static final String CHECKBOX_WIDTH_DECIMAL = "10(3)"
    public static final String TEXTAREA_DISPLAYED_BUT_SHOULD_BE_HIDDEN = "You have entered the value but this field should no longer be visible!"
    public static final String CALCULATION_NAME = "CALCULATION"
    public static final String CALCULATION_FORMULA = "radio_name/2"
    public static final String CALCULATION_CALCULATION = "func: (radio_name/2)"



    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()


    def "create new form"() {
        CaseReportForm form = CaseReportForm.build(FORM_NAME) {
            version VERSION
            versionDescription VERSION_DESCRIPTION
            revisionNotes REVISION_NOTES

            section(SECTION_LABEL) {
                title SECTION_TITLE
                subtitle SECTION_SUBTITLE
                instructions SECTION_INSTRUCTIONS
                pageNumber SECTION_PAGE_NUMBER

                group(GROUP_LABEL) {
                    hide true

                    text(TEXT_NAME) {
                        dataType partialDate
                        description TEXT_DESCRIPTION
                        question TEXT_ITEM_LEFT
                        units TEXT_UNITS
                        instructions TEXT_ITEM_RIGHT
                        header TEXT_ITEM_HEADER
                        subheader TEXT_ITEM_SUBHEADER
                        questionNumber TEXT_ITEM_QUESTION_NUMBER
                        length 50
                    }

                    checkbox(CHECKBOX_NAME) {
                        layout horizontal
                        value CHECKBOX_VALUE
                        digits 10, 3
                        regexp "[A-Z]{3}", REGEXP_ERROR
                        phi true
                        required true
                    }

                    radio(RADIO_NAME) {
                        validate(RADIO_VALIDATION_MESSAGE) { lte(RADIO_MAX) }
                        options 'First Option'  : 1,
                                'Best Option'   : 2
                    }

                    row {
                        multiSelect(ROW_1_NAME)
                        singleSelect(ROW_2_NAME)
                        textarea(ROW_3_NAME) {
                            show {
                                when RADIO_NAME is 2 otherwise TEXTAREA_DISPLAYED_BUT_SHOULD_BE_HIDDEN
                            }
                        }
                    }

                    calculation (CALCULATION_NAME){
                        formula CALCULATION_FORMULA
                    }
                }

                grid(GRID_LABEL) {
                    header GRID_HEADER
                    rows GRID_REPEAT_NUM
                    upto GRID_REPEAT_MAX
                }

            }
        }


        expect:
        form.name == FORM_NAME
        form.version == VERSION
        form.versionDescription == VERSION_DESCRIPTION
        form.revisionNotes == REVISION_NOTES

        when:
        Section section = form.sections[SECTION_LABEL]

        then:
        section
        section.label == SECTION_LABEL
        section.title == SECTION_TITLE
        section.subtitle == SECTION_SUBTITLE
        section.pageNumber == SECTION_PAGE_NUMBER
        section.caseReportForm == form

        when:
        Group group = section.groups[GROUP_LABEL]

        then:
        group
        group.label == GROUP_LABEL
        group.displayStatus == DisplayStatus.HIDE

        when:
        Item text = group.items[TEXT_NAME]

        then:
        text
        text.responseType == ResponseType.TEXT
        text.dataType == TEXT_ITEM_DATA_TYPE
        text.name == TEXT_NAME
        text.descriptionLabel == TEXT_DESCRIPTION
        text.leftItemText == TEXT_ITEM_LEFT
        text.rightItemText == TEXT_ITEM_RIGHT
        text.units == TEXT_UNITS
        text.header == TEXT_ITEM_HEADER
        text.subheader == TEXT_ITEM_SUBHEADER
        text.widthDecimal == TEXT_WIDTH_DECIMAL

        when:
        Item row1 = group.items[ROW_1_NAME]
        Item row2 = group.items[ROW_2_NAME]
        Item row3 = group.items[ROW_3_NAME]

        then:
        row1
        row1.columnNumber == 1
        row2
        row2.columnNumber == 2
        row3
        row3.columnNumber == 3
        row3.simpleConditionalDisplay == "$RADIO_NAME,2,$TEXTAREA_DISPLAYED_BUT_SHOULD_BE_HIDDEN".toString()

        when:
        Item checkbox = group.items[CHECKBOX_NAME]

        then:
        checkbox
        checkbox.responseType == ResponseType.CHECKBOX
        checkbox.responseLayout == ResponseLayout.HORIZONTAL
        checkbox.defaultValue == CHECKBOX_VALUE
        checkbox.widthDecimal == CHECKBOX_WIDTH_DECIMAL
        checkbox.validation == REGEXP_VALIDATION
        checkbox.validationErrorMessage == REGEXP_ERROR
        checkbox.phi
        checkbox.required

        when:
        Item radio = group.items[RADIO_NAME]

        then:
        radio
        radio.responseType == ResponseType.RADIO
        radio.validation == RADIO_VALIDATION
        radio.validationErrorMessage == RADIO_VALIDATION_MESSAGE
        radio.responseOptions
        radio.responseOptions.size() == 2
        radio.responseOptions[0].text == 'First Option'
        radio.responseOptions[0].value == '1'
        radio.responseOptions[1].text == 'Best Option'
        radio.responseOptions[1].value == '2'
        radio.responseOptionsText == 'First Option,Best Option'
        radio.responseValuesOrCalculations == '1,2'

        when:
        Item calculation = group.items[CALCULATION_NAME]

        then:
        calculation
        calculation.responseValuesOrCalculations == CALCULATION_CALCULATION

        when:
        Group grid = section.groups[GRID_LABEL]

        then:
        grid
        grid instanceof GridGroup
        grid.label == GRID_LABEL
        grid.header == GRID_HEADER
        grid.repeatNum == GRID_REPEAT_NUM
        grid.repeatMax == GRID_REPEAT_MAX


    }


    @Unroll
    def "class #clazz is accepted as data type #dataType"() {
        Text item = new CaseReportForm().section('section').text('text')
        CaseReportFormExtensions.dataType(item, clazz)

        expect:
        dataType == item.dataType

        where:
        clazz       | dataType
        String      | DataType.ST
        int         | DataType.INT
        Integer     | DataType.INT
        long        | DataType.INT
        Long        | DataType.INT
        BigInteger  | DataType.INT
        double      | DataType.REAL
        Double      | DataType.REAL
        float       | DataType.REAL
        Float       | DataType.REAL
        BigDecimal  | DataType.REAL
        Date        | DataType.DATE
        SqlDate     | DataType.DATE

    }


    def "load dsl file"() {
        File file = temporaryFolder.newFile('test.crf')

        //language=Groovy
        file << '''
            name 'Test Form'
            version '1.1'
            section('Test Section') {
                title 'Test Section Description'
            }
        '''

        CaseReportForm form = CaseReportForm.load(file)

        expect:
        form.name == 'Test Form'
        form.version == '1.1'
        form.sections.size() == 1

    }

}
