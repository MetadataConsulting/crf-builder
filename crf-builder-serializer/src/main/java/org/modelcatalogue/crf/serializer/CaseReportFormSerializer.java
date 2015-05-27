package org.modelcatalogue.crf.serializer;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.modelcatalogue.crf.model.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class exports in memory form model to excel file.
 */
public class CaseReportFormSerializer {


    private static final String SHEET_CRF = "CRF";
    private static final String HEADING_CRF_NAME = "CRF_NAME";
    private static final String HEADING_CRF_VERSION = "VERSION";
    private static final String HEADING_CRF_VERSION_DESCRIPTION = "VERSION_DESCRIPTION";
    private static final String HEADING_CRF_REVISION_NOTES = "REVISION_NOTES";
    private static final int HEADING_CRF_NAME_INDEX = 0;
    private static final int HEADING_CRF_VERSION_INDEX = 1;
    private static final int HEADING_CRF_VERSION_DESCRIPTION_INDEX = 2;
    private static final int HEADING_CRF_REVISION_NOTES_INDEX = 3;

    private static final String SHEET_SECTIONS = "Sections";
    private static final String SECTION_LABEL = "SECTION_LABEL";
    private static final String SECTION_TITLE = "SECTION_TITLE";
    private static final String SECTION_SUBTITLE = "SUBTITLE";
    private static final String SECTION_INSTRUCTIONS = "INSTRUCTIONS";
    private static final String SECTION_PAGE_NUMBER = "PAGE_NUMBER";
    private static final String SECTION_PARENT_SECTION = "PARENT_SECTION";
    private static final int SECTION_LABEL_INDEX = 0;
    private static final int SECTION_TITLE_INDEX = 1;
    private static final int SECTION_SUBTITLE_INDEX = 2;
    private static final int SECTION_INSTRUCTIONS_INDEX = 3;
    private static final int SECTION_PAGE_NUMBER_INDEX = 4;
    private static final int SECTION_PARENT_SECTION_INDEX = 5;

    private static final String SHEET_GROUPS = "Groups";
    private static final String GROUP_LABEL = "GROUP_LABEL";
    private static final String GROUP_LAYOUT = "GROUP_LAYOUT";
    private static final String GROUP_HEADER = "GROUP_HEADER";
    private static final String GROUP_REPEAT_NUMBER = "GROUP_REPEAT_NUMBER";
    private static final String GROUP_REPEAT_MAX = "GROUP_REPEAT_MAX";
    private static final String GROUP_DISPLAY_STATUS = "GROUP_DISPLAY_STATUS";
    private static final int GROUP_LABEL_INDEX = 0;
    private static final int GROUP_LAYOUT_INDEX = 1;
    private static final int GROUP_HEADER_INDEX = 2;
    private static final int GROUP_REPEAT_NUMBER_INDEX = 3;
    private static final int GROUP_REPEAT_MAX_INDEX = 4;
    private static final int GROUP_DISPLAY_STATUS_INDEX  = 5;

    private static final String GROUP_LAYOUT_GRID = "GRID";

    private static final String SHEET_ITEMS = "Items";
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
    private static final int ITEM_NAME_INDEX = 0;
    private static final int ITEM_DESCRIPTION_LABEL_INDEX = 1;
    private static final int ITEM_LEFT_ITEM_TEXT_INDEX = 2;
    private static final int ITEM_UNITS_INDEX = 3;
    private static final int ITEM_RIGHT_ITEM_TEXT_INDEX = 4;
    private static final int ITEM_SECTION_LABEL_INDEX = 5;
    private static final int ITEM_GROUP_LABEL_INDEX = 6;
    private static final int ITEM_HEADER_INDEX = 7;
    private static final int ITEM_SUBHEADER_INDEX = 8;
    private static final int ITEM_PARENT_ITEM_INDEX = 9;
    private static final int ITEM_COLUMN_NUMBER_INDEX = 10;
    private static final int ITEM_PAGE_NUMBER_INDEX = 11;
    private static final int ITEM_QUESTION_NUMBER_INDEX = 12;
    private static final int ITEM_RESPONSE_TYPE_INDEX = 13;
    private static final int ITEM_RESPONSE_LABEL_INDEX = 14;
    private static final int ITEM_RESPONSE_OPTIONS_TEXT_INDEX = 15;
    private static final int ITEM_RESPONSE_VALUES_OR_CALCULATIONS_INDEX = 16;
    private static final int ITEM_RESPONSE_LAYOUT_INDEX = 17;
    private static final int ITEM_DEFAULT_VALUE_INDEX = 18;
    private static final int ITEM_DATA_TYPE_INDEX = 19;
    private static final int ITEM_WIDTH_DECIMAL_INDEX = 20;
    private static final int ITEM_VALIDATION_INDEX = 21;
    private static final int ITEM_VALIDATION_ERROR_MESSAGE_INDEX = 22;
    private static final int ITEM_PHI_INDEX = 23;
    private static final int ITEM_REQUIRED_INDEX = 24;
    private static final int ITEM_DISPLAY_STATUS_INDEX = 25;
    private static final int ITEM_SIMPLE_CONDITIONAL_DISPLAY_INDEX = 26;

    private static final String INSTRUCTIONS_SHEET = "Instructions";
    private static final String INSTRUCTIONS_INFO = "Generated by CRF Builder (https://github.com/MetadataRegistry/crf-builder)";
    private static final String INSTRUCTIONS_VERSION = "3.1.3";
    private static final String INSTRUCTIONS_VERSION_VALUE = "Version: " + INSTRUCTIONS_VERSION;
    private static final int INSTRUCTIONS_INFO_ROW = 0;
    private static final int INSTRUCTIONS_INFO_COL = 0;
    private static final int INSTRUCTIONS_VERSION_ROW = 1;
    private static final int INSTRUCTIONS_VERSION_COL = 0;

    private final CaseReportForm form;

    /**
     * Creates new serializer for given form.
     * @param form form to be serialized
     */
    public CaseReportFormSerializer(CaseReportForm form) {
        this.form = form;
    }

    /**
     * Writes the form as excel file to the output stream, usually the FileOutputStream.
     * @param outputStream output stream to write the form to
     */
    public void write(OutputStream outputStream) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet crfSheet = workbook.createSheet(SHEET_CRF);
        HSSFRow crfHeadingsRow = crfSheet.createRow(0);

        crfHeadingsRow.createCell(HEADING_CRF_NAME_INDEX).setCellValue(HEADING_CRF_NAME);
        crfHeadingsRow.createCell(HEADING_CRF_VERSION_INDEX).setCellValue(HEADING_CRF_VERSION);
        crfHeadingsRow.createCell(HEADING_CRF_VERSION_DESCRIPTION_INDEX).setCellValue(HEADING_CRF_VERSION_DESCRIPTION);
        crfHeadingsRow.createCell(HEADING_CRF_REVISION_NOTES_INDEX).setCellValue(HEADING_CRF_REVISION_NOTES);

        HSSFRow crfRow = crfSheet.createRow(1);

        crfRow.createCell(HEADING_CRF_NAME_INDEX).setCellValue(form.getName());
        crfRow.createCell(HEADING_CRF_VERSION_INDEX).setCellValue(form.getVersion());
        crfRow.createCell(HEADING_CRF_VERSION_DESCRIPTION_INDEX).setCellValue(form.getVersionDescription());
        crfRow.createCell(HEADING_CRF_REVISION_NOTES_INDEX).setCellValue(form.getRevisionNotes());

        HSSFSheet sectionsSheet = workbook.createSheet(SHEET_SECTIONS);
        HSSFRow sectionHeadingsRow = sectionsSheet.createRow(0);

        sectionHeadingsRow.createCell(SECTION_LABEL_INDEX).setCellValue(SECTION_LABEL);
        sectionHeadingsRow.createCell(SECTION_TITLE_INDEX).setCellValue(SECTION_TITLE);
        sectionHeadingsRow.createCell(SECTION_SUBTITLE_INDEX).setCellValue(SECTION_SUBTITLE);
        sectionHeadingsRow.createCell(SECTION_INSTRUCTIONS_INDEX).setCellValue(SECTION_INSTRUCTIONS);
        sectionHeadingsRow.createCell(SECTION_PAGE_NUMBER_INDEX).setCellValue(SECTION_PAGE_NUMBER);
        sectionHeadingsRow.createCell(SECTION_PARENT_SECTION_INDEX).setCellValue(SECTION_PARENT_SECTION);

        HSSFSheet groupsSheet = workbook.createSheet(SHEET_GROUPS);
        HSSFRow groupHeadingsRow = groupsSheet.createRow(0);
        groupHeadingsRow.createCell(GROUP_LABEL_INDEX).setCellValue(GROUP_LABEL);
        groupHeadingsRow.createCell(GROUP_LAYOUT_INDEX).setCellValue(GROUP_LAYOUT);
        groupHeadingsRow.createCell(GROUP_HEADER_INDEX).setCellValue(GROUP_HEADER);
        groupHeadingsRow.createCell(GROUP_REPEAT_NUMBER_INDEX).setCellValue(GROUP_REPEAT_NUMBER);
        groupHeadingsRow.createCell(GROUP_REPEAT_MAX_INDEX).setCellValue(GROUP_REPEAT_MAX);
        groupHeadingsRow.createCell(GROUP_DISPLAY_STATUS_INDEX ).setCellValue(GROUP_DISPLAY_STATUS);


        HSSFSheet itemsSheet = workbook.createSheet(SHEET_ITEMS);
        HSSFRow itemsHeadingsRow = itemsSheet.createRow(0);
        itemsHeadingsRow.createCell(ITEM_NAME_INDEX).setCellValue(ITEM_NAME);
        itemsHeadingsRow.createCell(ITEM_DESCRIPTION_LABEL_INDEX).setCellValue(ITEM_DESCRIPTION_LABEL);
        itemsHeadingsRow.createCell(ITEM_LEFT_ITEM_TEXT_INDEX).setCellValue(ITEM_LEFT_ITEM_TEXT);
        itemsHeadingsRow.createCell(ITEM_UNITS_INDEX).setCellValue(ITEM_UNITS);
        itemsHeadingsRow.createCell(ITEM_RIGHT_ITEM_TEXT_INDEX).setCellValue(ITEM_RIGHT_ITEM_TEXT);
        itemsHeadingsRow.createCell(ITEM_SECTION_LABEL_INDEX).setCellValue(ITEM_SECTION_LABEL);
        itemsHeadingsRow.createCell(ITEM_GROUP_LABEL_INDEX).setCellValue(ITEM_GROUP_LABEL);
        itemsHeadingsRow.createCell(ITEM_HEADER_INDEX).setCellValue(ITEM_HEADER);
        itemsHeadingsRow.createCell(ITEM_SUBHEADER_INDEX).setCellValue(ITEM_SUBHEADER);
        itemsHeadingsRow.createCell(ITEM_PARENT_ITEM_INDEX).setCellValue(ITEM_PARENT_ITEM);
        itemsHeadingsRow.createCell(ITEM_COLUMN_NUMBER_INDEX).setCellValue(ITEM_COLUMN_NUMBER);
        itemsHeadingsRow.createCell(ITEM_PAGE_NUMBER_INDEX).setCellValue(ITEM_PAGE_NUMBER);
        itemsHeadingsRow.createCell(ITEM_QUESTION_NUMBER_INDEX).setCellValue(ITEM_QUESTION_NUMBER);
        itemsHeadingsRow.createCell(ITEM_RESPONSE_TYPE_INDEX).setCellValue(ITEM_RESPONSE_TYPE);
        itemsHeadingsRow.createCell(ITEM_RESPONSE_LABEL_INDEX).setCellValue(ITEM_RESPONSE_LABEL);
        itemsHeadingsRow.createCell(ITEM_RESPONSE_OPTIONS_TEXT_INDEX).setCellValue(ITEM_RESPONSE_OPTIONS_TEXT);
        itemsHeadingsRow.createCell(ITEM_RESPONSE_VALUES_OR_CALCULATIONS_INDEX).setCellValue(ITEM_RESPONSE_VALUES_OR_CALCULATIONS);
        itemsHeadingsRow.createCell(ITEM_RESPONSE_LAYOUT_INDEX).setCellValue(ITEM_RESPONSE_LAYOUT);
        itemsHeadingsRow.createCell(ITEM_DEFAULT_VALUE_INDEX).setCellValue(ITEM_DEFAULT_VALUE);
        itemsHeadingsRow.createCell(ITEM_DATA_TYPE_INDEX).setCellValue(ITEM_DATA_TYPE);
        itemsHeadingsRow.createCell(ITEM_WIDTH_DECIMAL_INDEX).setCellValue(ITEM_WIDTH_DECIMAL);
        itemsHeadingsRow.createCell(ITEM_VALIDATION_INDEX).setCellValue(ITEM_VALIDATION);
        itemsHeadingsRow.createCell(ITEM_VALIDATION_ERROR_MESSAGE_INDEX).setCellValue(ITEM_VALIDATION_ERROR_MESSAGE);
        itemsHeadingsRow.createCell(ITEM_PHI_INDEX).setCellValue(ITEM_PHI);
        itemsHeadingsRow.createCell(ITEM_REQUIRED_INDEX).setCellValue(ITEM_REQUIRED);
        itemsHeadingsRow.createCell(ITEM_DISPLAY_STATUS_INDEX).setCellValue(ITEM_DISPLAY_STATUS);
        itemsHeadingsRow.createCell(ITEM_SIMPLE_CONDITIONAL_DISPLAY_INDEX).setCellValue(ITEM_SIMPLE_CONDITIONAL_DISPLAY);

        int sectionCounter = 1;
        int groupCounter = 1;
        int itemCounter = 1;
        for (Section section : form.getSections().values()) {
            HSSFRow sectionRow = sectionsSheet.createRow(sectionCounter);

            sectionRow.createCell(SECTION_LABEL_INDEX).setCellValue(section.getLabel());
            sectionRow.createCell(SECTION_TITLE_INDEX).setCellValue(section.getTitle());
            sectionRow.createCell(SECTION_SUBTITLE_INDEX).setCellValue(section.getSubtitle());
            sectionRow.createCell(SECTION_INSTRUCTIONS_INDEX).setCellValue(section.getInstructions());
            sectionRow.createCell(SECTION_PAGE_NUMBER_INDEX).setCellValue(section.getPageNumber());

            for (Group group : section.getGroups().values()) {
                HSSFRow groupRow = groupsSheet.createRow(groupCounter);

                groupRow.createCell(GROUP_LABEL_INDEX).setCellValue(group.getLabel());
                if (DisplayStatus.HIDE.equals(group.getDisplayStatus())) {
                    groupRow.createCell(GROUP_DISPLAY_STATUS_INDEX ).setCellValue(group.getDisplayStatus().toString());
                }

                if (group instanceof GridGroup) {
                    GridGroup grid = (GridGroup) group;
                    groupRow.createCell(GROUP_LAYOUT_INDEX).setCellValue(GROUP_LAYOUT_GRID);
                    groupRow.createCell(GROUP_HEADER_INDEX).setCellValue(grid.getHeader());
                    if (grid.getRepeatNum() != null) {
                        groupRow.createCell(GROUP_REPEAT_NUMBER_INDEX).setCellValue(grid.getRepeatNum());
                    } else {
                        groupRow.createCell(GROUP_REPEAT_NUMBER_INDEX).setCellValue("");
                    }
                    if (grid.getRepeatMax() != null) {
                        groupRow.createCell(GROUP_REPEAT_MAX_INDEX).setCellValue(grid.getRepeatMax());
                    } else {
                        groupRow.createCell(GROUP_REPEAT_MAX_INDEX).setCellValue("");
                    }

                } else {
                    groupRow.createCell(GROUP_LAYOUT_INDEX).setCellValue("");
                    groupRow.createCell(GROUP_HEADER_INDEX).setCellValue("");
                    groupRow.createCell(GROUP_REPEAT_NUMBER_INDEX).setCellValue("");
                    groupRow.createCell(GROUP_REPEAT_MAX_INDEX).setCellValue("");
                }

                groupCounter++;
            }

            for (Item item : section.getItems().values()) {
                HSSFRow itemRow = itemsSheet.createRow(itemCounter);

                itemRow.createCell(ITEM_NAME_INDEX).setCellValue(item.getName());
                itemRow.createCell(ITEM_DESCRIPTION_LABEL_INDEX).setCellValue(item.getDescriptionLabel());
                itemRow.createCell(ITEM_LEFT_ITEM_TEXT_INDEX).setCellValue(item.getLeftItemText());
                itemRow.createCell(ITEM_UNITS_INDEX).setCellValue(item.getUnits());
                itemRow.createCell(ITEM_RIGHT_ITEM_TEXT_INDEX).setCellValue(item.getRightItemText());
                itemRow.createCell(ITEM_SECTION_LABEL_INDEX).setCellValue(item.getSection().getLabel());
                itemRow.createCell(ITEM_HEADER_INDEX).setCellValue(item.getHeader());
                itemRow.createCell(ITEM_SUBHEADER_INDEX).setCellValue(item.getSubheader());
                itemRow.createCell(ITEM_PARENT_ITEM_INDEX).setCellValue("");
                itemRow.createCell(ITEM_PAGE_NUMBER_INDEX).setCellValue(item.getPageNumber());
                itemRow.createCell(ITEM_QUESTION_NUMBER_INDEX).setCellValue(item.getQuestionNumber());
                itemRow.createCell(ITEM_RESPONSE_TYPE_INDEX).setCellValue(item.getResponseType().getExcelValue());
                itemRow.createCell(ITEM_RESPONSE_LABEL_INDEX).setCellValue(item.getResponseLabel());
                itemRow.createCell(ITEM_RESPONSE_OPTIONS_TEXT_INDEX).setCellValue(item.getResponseOptionsText());
                itemRow.createCell(ITEM_RESPONSE_VALUES_OR_CALCULATIONS_INDEX).setCellValue(item.getResponseValuesOrCalculations());
                itemRow.createCell(ITEM_DEFAULT_VALUE_INDEX).setCellValue(item.getDefaultValue());
                itemRow.createCell(ITEM_DATA_TYPE_INDEX).setCellValue(item.getDataType().toString());
                itemRow.createCell(ITEM_WIDTH_DECIMAL_INDEX).setCellValue(item.getWidthDecimal());
                itemRow.createCell(ITEM_VALIDATION_INDEX).setCellValue(item.getValidation());
                itemRow.createCell(ITEM_VALIDATION_ERROR_MESSAGE_INDEX).setCellValue(item.getValidationErrorMessage());
                itemRow.createCell(ITEM_SIMPLE_CONDITIONAL_DISPLAY_INDEX).setCellValue(item.getSimpleConditionalDisplay());
                itemRow.createCell(ITEM_RESPONSE_LAYOUT_INDEX).setCellValue(nullSafe(item.getResponseLayout()));
                itemRow.createCell(ITEM_DATA_TYPE_INDEX).setCellValue(nullSafe(item.getDataType()));
                itemRow.createCell(ITEM_DISPLAY_STATUS_INDEX).setCellValue(nullSafe(item.getDisplayStatus()));

                if (item.getGroup() != null) {
                    itemRow.createCell(ITEM_GROUP_LABEL_INDEX).setCellValue(item.getGroup().getLabel());
                } else {
                    itemRow.createCell(ITEM_GROUP_LABEL_INDEX).setCellValue("");
                }

                if (item.getPhi() != null) {
                    itemRow.createCell(ITEM_PHI_INDEX).setCellValue(item.getPhi());
                } else {
                    itemRow.createCell(ITEM_PHI_INDEX).setCellValue("");
                }

                if (item.getRequired() != null) {
                    itemRow.createCell(ITEM_REQUIRED_INDEX).setCellValue(item.getRequired());
                } else {
                    itemRow.createCell(ITEM_REQUIRED_INDEX).setCellValue("");
                }

                if (item.getColumnNumber() != null) {
                    itemRow.createCell(ITEM_COLUMN_NUMBER_INDEX).setCellValue(item.getColumnNumber());
                } else {
                    itemRow.createCell(ITEM_COLUMN_NUMBER_INDEX).setCellValue("");
                }

                itemCounter++;
            }

            sectionCounter++;
        }

        HSSFSheet instructionsSheet = workbook.createSheet(INSTRUCTIONS_SHEET);
        HSSFRow instructionsInfoRow = instructionsSheet.createRow(INSTRUCTIONS_INFO_ROW);
        HSSFCell instructionsInfoCell = instructionsInfoRow.createCell(INSTRUCTIONS_INFO_COL);
        instructionsInfoCell.setCellValue(INSTRUCTIONS_INFO);

        HSSFRow instructionsVersionRow = instructionsSheet.createRow(INSTRUCTIONS_VERSION_ROW);
        HSSFCell instructionsVersionCell = instructionsVersionRow.createCell(INSTRUCTIONS_VERSION_COL);
        instructionsVersionCell.setCellValue(INSTRUCTIONS_VERSION_VALUE);

        makeHeaderRows(crfHeadingsRow, groupHeadingsRow, sectionHeadingsRow, itemsHeadingsRow);

        workbook.write(outputStream);
    }

    private static void makeHeaderRows(HSSFRow... rows) {
        for (HSSFRow row : rows) {
            HSSFFont boldFont = row.getSheet().getWorkbook().createFont();
            boldFont.setBold(true);
            boldFont.setFontName(HSSFFont.FONT_ARIAL);
            boldFont.setFontHeightInPoints((short)10);

            HSSFCellStyle leftCellStyle = row.getSheet().getWorkbook().createCellStyle();
            leftCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            leftCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            leftCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            leftCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            leftCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            leftCellStyle.setFont(boldFont);

            HSSFCellStyle middleCellStyle = row.getSheet().getWorkbook().createCellStyle();
            middleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            middleCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            middleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            middleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            middleCellStyle.setFont(boldFont);

            HSSFCellStyle rightCellStyle = row.getSheet().getWorkbook().createCellStyle();
            rightCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            rightCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            rightCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            rightCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            rightCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            rightCellStyle.setFont(boldFont);

            for (int i = row.getFirstCellNum() ; i < row.getLastCellNum() ; i++) {
                HSSFCell cell = row.getCell(i);
                if (i == row.getFirstCellNum()) {
                    cell.setCellStyle(leftCellStyle);
                } else if (i == row.getLastCellNum() - 1) {
                    cell.setCellStyle(rightCellStyle);
                } else {
                    cell.setCellStyle(middleCellStyle);
                }
                row.getSheet().autoSizeColumn(i);
            }
        }
    }

    private static <E extends Enum<E>> String nullSafe(Enum<E> enumValue) {
        if (enumValue != null) {
            return enumValue.toString();
        }
        return "";
    }
}
