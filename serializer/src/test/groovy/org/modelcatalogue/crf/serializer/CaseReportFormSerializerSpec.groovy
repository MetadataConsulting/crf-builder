package org.modelcatalogue.crf.serializer

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.modelcatalogue.crf.model.CaseReportForm
import spock.lang.Shared
import spock.lang.Specification

class CaseReportFormSerializerSpec extends Specification {

    @Shared HSSFWorkbook workbook

    def setupSpec() {
        CaseReportForm form = buildTestForm()

        ByteArrayOutputStream output = new ByteArrayOutputStream()
        new CaseReportFormSerializer(form).write(output)

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray())
        workbook = new HSSFWorkbook(input)
    }

    def "CRF sheet is written"() {
        when:
        HSSFSheet crfSheet = workbook.getSheet(SHEET_CRF)

        then:
        crfSheet

        when:
        HSSFRow crfHeadingRow = crfSheet.getRow(crfSheet.getFirstRowNum())

        then:
        crfHeadingRow

        when:
        HSSFCell crfNameHeading = crfHeadingRow.cellIterator().find { it.stringCellValue == HEADING_CRF_NAME }
        HSSFCell crfVersionHeading = crfHeadingRow.cellIterator().find { it.stringCellValue == HEADING_CRF_VERSION }
        HSSFCell crfVersionDescriptionHeading = crfHeadingRow.cellIterator().find { it.stringCellValue == HEADING_CRF_VERSION_DESCRIPTION }
        HSSFCell crfRevisionNotesHeading = crfHeadingRow.cellIterator().find { it.stringCellValue == HEADING_CRF_REVISION_NOTES }

        then:
        crfNameHeading
        crfVersionHeading
        crfVersionDescriptionHeading
        crfRevisionNotesHeading

        when:
        HSSFRow crfMetadataRow = crfSheet.getRow(crfSheet.getLastRowNum())

        then:
        crfHeadingRow

        when:
        HSSFCell crfName = crfMetadataRow.getCell(crfNameHeading.columnIndex)
        HSSFCell crfVersion = crfMetadataRow.getCell(crfVersionHeading.columnIndex)
        HSSFCell crfVersionDescription = crfMetadataRow.getCell(crfVersionDescriptionHeading.columnIndex)
        HSSFCell crfRevisionNotes = crfMetadataRow.getCell(crfRevisionNotesHeading.columnIndex)

        then:
        crfName
        crfName.stringCellValue == CRF_NAME
        crfVersion
        crfVersion.stringCellValue == CRF_VERSION
        crfVersionDescription
        crfVersionDescription.stringCellValue == CRF_VERSION_DESCRIPTION
        crfRevisionNotes
        crfRevisionNotes.stringCellValue == CRF_REVISION_NOTES

    }

    def "sections sheet is written"() {

        when:
        HSSFSheet sectionsSheet = workbook.getSheet(SHEET_SECTIONS)

        then:
        sectionsSheet

        when:
        HSSFRow sectionsHeadingRow = sectionsSheet.getRow(sectionsSheet.firstRowNum)

        then:
        sectionsHeadingRow

        when:
        HSSFCell sectionLabelHeading = sectionsHeadingRow.cellIterator().find { it.stringCellValue == SECTION_LABEL }
        HSSFCell sectionTitleHeading = sectionsHeadingRow.cellIterator().find { it.stringCellValue == SECTION_TITLE }
        HSSFCell sectionSubtitleHeading = sectionsHeadingRow.cellIterator().find { it.stringCellValue == SECTION_SUBTITLE }
        HSSFCell sectionInstructionsHeading = sectionsHeadingRow.cellIterator().find { it.stringCellValue == SECTION_INSTRUCTIONS }
        HSSFCell sectionPageNumberHeading = sectionsHeadingRow.cellIterator().find { it.stringCellValue == SECTION_PAGE_NUMBER }
        HSSFCell sectionParentSectionHeading = sectionsHeadingRow.cellIterator().find { it.stringCellValue == SECTION_PARENT_SECTION }

        then:
        sectionLabelHeading
        sectionTitleHeading
        sectionSubtitleHeading
        sectionInstructionsHeading
        sectionPageNumberHeading
        sectionParentSectionHeading

        when:
        HSSFRow section1Row = sectionsSheet.getRow(sectionsSheet.firstRowNum + 1)

        then:
        section1Row

        when:
        HSSFCell section1Label = section1Row.getCell(sectionLabelHeading.columnIndex)
        HSSFCell section1Title = section1Row.getCell(sectionTitleHeading.columnIndex)
        HSSFCell section1Subtitle = section1Row.getCell(sectionSubtitleHeading.columnIndex)
        HSSFCell section1Instructions = section1Row.getCell(sectionInstructionsHeading.columnIndex)
        HSSFCell section1PageNumber = section1Row.getCell(sectionPageNumberHeading.columnIndex)

        then:
        section1Label
        section1Label.stringCellValue == SECTION_LABEL_1
        section1Title
        section1Title.stringCellValue == SECTION_TITLE_1
        section1Subtitle
        section1Subtitle.stringCellValue == SECTION_SUBTITLE_1
        section1Instructions
        section1Instructions.stringCellValue == SECTION_INSTRUCTIONS_1
        section1PageNumber
        section1PageNumber.stringCellValue == SECTION_PAGE_NUMBER_1

        when:
        HSSFRow section2Row = sectionsSheet.getRow(sectionsSheet.firstRowNum + 2)

        then:
        section2Row

        when:
        HSSFCell section2Label = section2Row.getCell(sectionLabelHeading.columnIndex)
        HSSFCell section2Title = section2Row.getCell(sectionTitleHeading.columnIndex)

        then:
        section2Label
        section2Label.stringCellValue == SECTION_LABEL_2
        section2Title
        section2Title.stringCellValue == SECTION_TITLE_2


        expect:
        sectionsSheet.firstRowNum + 3 == sectionsSheet.lastRowNum

        when:
        HSSFRow section3Row = sectionsSheet.getRow(sectionsSheet.lastRowNum)

        then:
        section3Row

        when:
        HSSFCell section3Label = section3Row.getCell(sectionLabelHeading.columnIndex)
        HSSFCell section3Title = section3Row.getCell(sectionTitleHeading.columnIndex)

        then:
        section3Label
        section3Label.stringCellValue == SECTION_LABEL_3
        section3Title
        section3Title.stringCellValue == SECTION_TITLE_3
    }

    def "groups sheet is written"() {
        when:
        HSSFSheet groupsSheet = workbook.getSheet(SHEET_GROUPS)

        then:
        groupsSheet

        when:
        HSSFRow groupsHeadingRow = groupsSheet.getRow(groupsSheet.firstRowNum)

        then:
        groupsHeadingRow

        when:
        HSSFCell groupLabelHeading = groupsHeadingRow.cellIterator().find { it.stringCellValue == GROUP_LABEL }
        HSSFCell groupLayoutHeading = groupsHeadingRow.cellIterator().find { it.stringCellValue == GROUP_LAYOUT }
        HSSFCell groupHeaderHeading = groupsHeadingRow.cellIterator().find { it.stringCellValue == GROUP_HEADER }
        HSSFCell groupRepeatNumberHeading = groupsHeadingRow.cellIterator().find { it.stringCellValue == GROUP_REPEAT_NUMBER }
        HSSFCell groupRepeatMaxHeading = groupsHeadingRow.cellIterator().find { it.stringCellValue == GROUP_REPEAT_MAX }
        HSSFCell groupDisplayStatusHeading = groupsHeadingRow.cellIterator().find { it.stringCellValue == GROUP_DISPLAY_STATUS }

        then:
        groupLabelHeading
        groupLayoutHeading
        groupHeaderHeading
        groupRepeatNumberHeading
        groupRepeatMaxHeading
        groupDisplayStatusHeading

        when:
        HSSFRow group1Row = groupsSheet.getRow(groupsSheet.lastRowNum)

        then:
        group1Row

        when:
        HSSFCell groupLabel = group1Row.getCell(groupLabelHeading.columnIndex)
        HSSFCell groupLayout = group1Row.getCell(groupLayoutHeading.columnIndex)
        HSSFCell groupHeader = group1Row.getCell(groupHeaderHeading.columnIndex)
        HSSFCell groupRepeatNumber = group1Row.getCell(groupRepeatNumberHeading.columnIndex)
        HSSFCell groupRepeatMax = group1Row.getCell(groupRepeatMaxHeading.columnIndex)
        HSSFCell groupDisplayStatus = group1Row.getCell(groupDisplayStatusHeading.columnIndex)

        then:
        groupLabel
        groupLabel.stringCellValue == GROUP_LABEL_1
        groupLayout
        groupLayout.stringCellValue == GROUP_LAYOUT_1
        groupHeader
        groupHeader.stringCellValue == GROUP_HEADER_1
        groupRepeatNumber
        groupRepeatNumber.cellType == HSSFCell.CELL_TYPE_NUMERIC
        groupRepeatNumber.numericCellValue == GROUP_REPEAT_NUMBER_1 as double
        groupRepeatMax
        groupRepeatMax.cellType == HSSFCell.CELL_TYPE_NUMERIC
        groupRepeatMax.numericCellValue == GROUP_REPEAT_MAX_1 as double
        groupDisplayStatus
        groupDisplayStatus.stringCellValue == GROUP_DISPLAY_STATUS_1
    }

    def "items sheet is written"() {

        when:
        HSSFSheet itemsSheet = workbook.getSheet(SHEET_ITEMS)

        then:
        itemsSheet

        when:
        HSSFRow itemHeadingsRow = itemsSheet.getRow(itemsSheet.firstRowNum)

        then:
        itemHeadingsRow

        when:
        HSSFCell itemNameHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_NAME }
        HSSFCell itemDescriptionHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_DESCRIPTION_LABEL }
        HSSFCell itemLeftItemTextHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_LEFT_ITEM_TEXT }
        HSSFCell itemUnitHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_UNITS }
        HSSFCell itemRightItemHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_RIGHT_ITEM_TEXT }
        HSSFCell itemSectionHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_SECTION_LABEL }
        HSSFCell itemGroupLabelHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_GROUP_LABEL }
        HSSFCell itemHeaderHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_HEADER }
        HSSFCell itemSubheaderHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_SUBHEADER }
        HSSFCell itemParentItemHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_PARENT_ITEM }
        HSSFCell itemColumnNumberHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_COLUMN_NUMBER }
        HSSFCell itemPageNumberHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_PAGE_NUMBER }
        HSSFCell itemQuestionNumberHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_QUESTION_NUMBER }
        HSSFCell itemResponseTypeHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_RESPONSE_TYPE }
        HSSFCell itemResponseLabelHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_RESPONSE_LABEL }
        HSSFCell itemResponseOptionsHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_RESPONSE_OPTIONS_TEXT }
        HSSFCell itemResponseValuesOrCalculationHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_RESPONSE_VALUES_OR_CALCULATIONS }
        HSSFCell itemResponseLayoutHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_RESPONSE_LAYOUT }
        HSSFCell itemDefaultValueHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_DEFAULT_VALUE }
        HSSFCell itemDataTypeHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_DATA_TYPE }
        HSSFCell itemWidthDecimalHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_WIDTH_DECIMAL }
        HSSFCell itemValidationHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_VALIDATION }
        HSSFCell itemValidationErrorMessageHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_VALIDATION_ERROR_MESSAGE }
        HSSFCell itemPhiHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_PHI }
        HSSFCell itemRequiredHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_REQUIRED }
        HSSFCell itemDisplayStatusHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_DISPLAY_STATUS }
        HSSFCell itemSimpleConditionalHeading = itemHeadingsRow.cellIterator().find { it.stringCellValue == ITEM_SIMPLE_CONDITIONAL_DISPLAY }

        then:
        itemNameHeading
        itemDescriptionHeading
        itemLeftItemTextHeading
        itemUnitHeading
        itemRightItemHeading
        itemSectionHeading
        itemGroupLabelHeading
        itemHeaderHeading
        itemSubheaderHeading
        itemParentItemHeading
        itemColumnNumberHeading
        itemPageNumberHeading
        itemQuestionNumberHeading
        itemResponseTypeHeading
        itemResponseLabelHeading
        itemResponseOptionsHeading
        itemResponseValuesOrCalculationHeading
        itemResponseLayoutHeading
        itemDefaultValueHeading
        itemDataTypeHeading
        itemWidthDecimalHeading
        itemValidationHeading
        itemValidationErrorMessageHeading
        itemPhiHeading
        itemRequiredHeading
        itemDisplayStatusHeading
        itemSimpleConditionalHeading

        when:
        HSSFRow pedatRow = itemsSheet.getRow(itemsSheet.firstRowNum + 1)

        then:
        pedatRow

        when:
        HSSFCell pedatName = pedatRow.getCell(itemNameHeading.columnIndex)
        HSSFCell pedatDescription = pedatRow.getCell(itemDescriptionHeading.columnIndex)
        HSSFCell pedatLeftItem = pedatRow.getCell(itemLeftItemTextHeading.columnIndex)
        HSSFCell pedatUnit = pedatRow.getCell(itemUnitHeading.columnIndex)
        HSSFCell pedatRightItem = pedatRow.getCell(itemRightItemHeading.columnIndex)
        HSSFCell pedatSection = pedatRow.getCell(itemSectionHeading.columnIndex)
        HSSFCell pedatGroupLabel = pedatRow.getCell(itemGroupLabelHeading.columnIndex)
        HSSFCell pedatHeader = pedatRow.getCell(itemHeaderHeading.columnIndex)
        HSSFCell pedatSubheader = pedatRow.getCell(itemSubheaderHeading.columnIndex)
        HSSFCell pedatParentItem = pedatRow.getCell(itemParentItemHeading.columnIndex)
        HSSFCell pedatColumnNumber = pedatRow.getCell(itemColumnNumberHeading.columnIndex)
        HSSFCell pedatPageNumber = pedatRow.getCell(itemPageNumberHeading.columnIndex)
        HSSFCell pedatQuestionNumber = pedatRow.getCell(itemQuestionNumberHeading.columnIndex)
        HSSFCell pedatResponseType = pedatRow.getCell(itemResponseTypeHeading.columnIndex)
        HSSFCell pedatResponseLabel = pedatRow.getCell(itemResponseLabelHeading.columnIndex)
        HSSFCell pedatResponseOptions = pedatRow.getCell(itemResponseOptionsHeading.columnIndex)
        HSSFCell pedatResponseValuesOrCalculation = pedatRow.getCell(itemResponseValuesOrCalculationHeading.columnIndex)
        HSSFCell pedatResponseLayout = pedatRow.getCell(itemResponseLayoutHeading.columnIndex)
        HSSFCell pedatDefaultValue = pedatRow.getCell(itemDefaultValueHeading.columnIndex)
        HSSFCell pedatDataType = pedatRow.getCell(itemDataTypeHeading.columnIndex)
        HSSFCell pedatWidthDecimal = pedatRow.getCell(itemWidthDecimalHeading.columnIndex)
        HSSFCell pedatValidation = pedatRow.getCell(itemValidationHeading.columnIndex)
        HSSFCell pedatValidationErrorMessage = pedatRow.getCell(itemValidationErrorMessageHeading.columnIndex)
        HSSFCell pedatPhi = pedatRow.getCell(itemPhiHeading.columnIndex)
        HSSFCell pedatRequired = pedatRow.getCell(itemRequiredHeading.columnIndex)
        HSSFCell pedatDisplayStatus = pedatRow.getCell(itemDisplayStatusHeading.columnIndex)
        HSSFCell pedatSimpleConditional = pedatRow.getCell(itemSimpleConditionalHeading.columnIndex)

        then:
        pedatName
        pedatName.stringCellValue == ITEM_NAME_1
        pedatDescription
        pedatDescription.stringCellValue == ITEM_DESCRIPTION_LABEL_1
        pedatLeftItem
        pedatLeftItem.stringCellValue == ITEM_LEFT_ITEM_TEXT_1
        pedatUnit
        pedatRightItem
        pedatSection
        pedatSection.stringCellValue == SECTION_LABEL_1
        pedatGroupLabel
        pedatHeader
        pedatHeader.stringCellValue == ITEM_HEADER_1
        pedatSubheader
        pedatParentItem
        pedatColumnNumber
        pedatColumnNumber.cellType == HSSFCell.CELL_TYPE_NUMERIC
        pedatColumnNumber.numericCellValue == ITEM_COLUMN_NUMBER_1 as double
        pedatPageNumber
        pedatQuestionNumber
        pedatQuestionNumber.cellType == HSSFCell.CELL_TYPE_STRING
        pedatQuestionNumber.stringCellValue == ITEM_QUESTION_NUMBER_1
        pedatResponseType
        pedatResponseType.stringCellValue == ITEM_RESPONSE_TYPE_1
        pedatResponseLabel
        pedatResponseOptions
        pedatResponseValuesOrCalculation
        pedatResponseLayout
        pedatDefaultValue
        pedatDataType
        pedatDataType.stringCellValue == ITEM_DATA_TYPE_1
        pedatWidthDecimal
        pedatValidation
        pedatValidationErrorMessage
        pedatPhi
        pedatRequired
        pedatDisplayStatus
        pedatSimpleConditional
    }

    def "write example sheet"() {
        when:
        File file = new File(System.getProperty('java.io.tmpdir'), 'crf-builder/example.xls')
        if (file.parentFile.exists()) {
            file.parentFile.deleteDir()
        }
        file.parentFile.mkdirs()
        file.createNewFile()

        workbook.write(new FileOutputStream(file))
        println "example file written to ${file.absolutePath}"

        then:
        noExceptionThrown()
    }

    private static final String SHEET_CRF = 'CRF'

    private static final String HEADING_CRF_NAME = 'CRF_NAME'
    private static final String HEADING_CRF_VERSION = 'VERSION'
    private static final String HEADING_CRF_VERSION_DESCRIPTION = 'VERSION_DESCRIPTION'
    private static final String HEADING_CRF_REVISION_NOTES = 'REVISION_NOTES'

    private static final String CRF_NAME = 'Sample Physical Exam'
    private static final String CRF_VERSION = 'English'
    private static final String CRF_VERSION_DESCRIPTION = 'Sample Physical Exam Version Description'
    private static final String CRF_REVISION_NOTES = 'htaycher 09-19-2012'

    private static final String SHEET_SECTIONS = 'Sections'

    private static final String SECTION_LABEL = "SECTION_LABEL"
    private static final String SECTION_TITLE = "SECTION_TITLE"
    private static final String SECTION_SUBTITLE = "SUBTITLE"
    private static final String SECTION_INSTRUCTIONS = "INSTRUCTIONS"
    private static final String SECTION_PAGE_NUMBER = "PAGE_NUMBER"
    private static final String SECTION_PARENT_SECTION = "PARENT_SECTION"

    private static final String SECTION_LABEL_1 = 'I Basic'
    private static final String SECTION_LABEL_2 = 'II Body System'
    private static final String SECTION_LABEL_3 = 'III Other'

    private static final String SECTION_TITLE_1 = 'Basic Information'
    private static final String SECTION_TITLE_2 = 'Body System/Site'
    private static final String SECTION_TITLE_3 = 'Specify Other Body System/Site'

    private static final String SECTION_SUBTITLE_1 = 'This is Basic Information Subtitle'
    private static final String SECTION_INSTRUCTIONS_1 = 'These are Basic Information Instructions'
    private static final String SECTION_PAGE_NUMBER_1 = '12'


    private static final String SHEET_GROUPS = 'Groups'
    private static final String GROUP_LABEL = "GROUP_LABEL";
    private static final String GROUP_LAYOUT = "GROUP_LAYOUT";
    private static final String GROUP_HEADER = "GROUP_HEADER";
    private static final String GROUP_REPEAT_NUMBER = "GROUP_REPEAT_NUMBER";
    private static final String GROUP_REPEAT_MAX = "GROUP_REPEAT_MAX";
    private static final String GROUP_DISPLAY_STATUS = "GROUP_DISPLAY_STATUS";

    private static final String GROUP_LABEL_1 = "Other Body System Site";
    private static final String GROUP_LAYOUT_1 = "GRID";
    private static final String GROUP_HEADER_1 = "Other Body System / Site";
    private static final int GROUP_REPEAT_NUMBER_1 = 5;
    private static final int GROUP_REPEAT_MAX_1 = 10;
    private static final String GROUP_DISPLAY_STATUS_1 = "HIDE";

    private static final String SHEET_ITEMS = 'Items'
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String ITEM_DESCRIPTION_LABEL = "DESCRIPTION_LABEL";
    private static final String ITEM_LEFT_ITEM_TEXT = "LEFT_ITEM_TEXT";
    private static final String ITEM_UNITS = "UNITS";
    private static final String ITEM_RIGHT_ITEM_TEXT = "RIGHT_ITEM_TEXT";
    private static final String ITEM_SECTION_LABEL = "SECTION_LABEL";
    private static final String ITEM_GROUP_LABEL = "GROUP_LABEL";
    private static final String ITEM_HEADER = "HEADER";
    private static final String ITEM_SUBHEADER = "SUBHEADER";
    private static final String ITEM_PARENT_ITEM = "PARENT_ITEM";
    private static final String ITEM_COLUMN_NUMBER = "COLUMN_NUMBER";
    private static final String ITEM_PAGE_NUMBER = "PAGE_NUMBER";
    private static final String ITEM_QUESTION_NUMBER = "QUESTION_NUMBER";
    private static final String ITEM_RESPONSE_TYPE = "RESPONSE_TYPE";
    private static final String ITEM_RESPONSE_LABEL = "RESPONSE_LABEL";
    private static final String ITEM_RESPONSE_OPTIONS_TEXT = "RESPONSE_OPTIONS_TEXT";
    private static final String ITEM_RESPONSE_VALUES_OR_CALCULATIONS = "RESPONSE_VALUES_OR_CALCULATIONS";
    private static final String ITEM_RESPONSE_LAYOUT = "RESPONSE_LAYOUT";
    private static final String ITEM_DEFAULT_VALUE = "DEFAULT_VALUE";
    private static final String ITEM_DATA_TYPE = "DATA_TYPE";
    private static final String ITEM_WIDTH_DECIMAL = "WIDTH_DECIMAL";
    private static final String ITEM_VALIDATION = "VALIDATION";
    private static final String ITEM_VALIDATION_ERROR_MESSAGE = "VALIDATION_ERROR_MESSAGE";
    private static final String ITEM_PHI = "PHI";
    private static final String ITEM_REQUIRED = "REQUIRED";
    private static final String ITEM_DISPLAY_STATUS = "ITEM_DISPLAY_STATUS";
    private static final String ITEM_SIMPLE_CONDITIONAL_DISPLAY = "SIMPLE_CONDITIONAL_DISPLAY";

    private static final String ITEM_NAME_1 = "PEDAT";
    private static final String ITEM_DESCRIPTION_LABEL_1 = "Date of Physical Exam";
    private static final String ITEM_LEFT_ITEM_TEXT_1 = "Date of Physical Examination:";
    private static final String ITEM_HEADER_1 = "Visit Information:";
    private static final int ITEM_COLUMN_NUMBER_1 = 1;
    private static final String ITEM_QUESTION_NUMBER_1 = "1";
    private static final String ITEM_RESPONSE_TYPE_1 = "text";
    private static final String ITEM_DATA_TYPE_1 = "DATE";

    CaseReportForm buildTestForm() {
        CaseReportForm.build(CRF_NAME) {
            version CRF_VERSION
            versionDescription CRF_VERSION_DESCRIPTION
            revisionNotes CRF_REVISION_NOTES

            section(SECTION_LABEL_1) {
                title SECTION_TITLE_1
                subtitle SECTION_SUBTITLE_1
                instructions SECTION_INSTRUCTIONS_1
                pageNumber SECTION_PAGE_NUMBER_1


                row {
                    text(ITEM_NAME_1) {
                        description ITEM_DESCRIPTION_LABEL_1
                        question ITEM_LEFT_ITEM_TEXT_1
                        header ITEM_HEADER_1
                        questionNumber ITEM_QUESTION_NUMBER_1
                        dataType date
                    }
                }
            }

            section(SECTION_LABEL_2) {
                title SECTION_TITLE_2
            }

            section(SECTION_LABEL_3) {
                title SECTION_TITLE_3
                grid (GROUP_LABEL_1) {
                    hide true
                    header GROUP_HEADER_1
                    rows GROUP_REPEAT_NUMBER_1
                    upto GROUP_REPEAT_MAX_1
                }
            }
        }

    }

}
