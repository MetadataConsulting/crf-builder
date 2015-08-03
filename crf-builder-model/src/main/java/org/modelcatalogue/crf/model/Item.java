package org.modelcatalogue.crf.model;

import org.modelcatalogue.crf.model.validation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;

import static org.modelcatalogue.crf.model.validation.ValidationConstants.ALPHA_NUMERIC_ENGLISH_NO_SPACES_PATTERN;
import static org.modelcatalogue.crf.model.validation.ValidationConstants.WIDTH_DECIMAL_PATTERN;

@ValidDefaultValue
@ValidWidthDecimal
@ValidValidationMessage
@ValidResponseType
public class Item implements Text, Textarea, SingleSelect, Radio, MultiSelect, Checkbox, Calculation, File, InstantCalculation, GroupCalculation {

    private static final String CALCULATION_RESPONSE_OPTIONS_TEXT = "calculation";
    private static final String RESPONSE_LABEL_SUFFIX = "_RL";

    Item(ResponseType type) {
        this.responseType = type;
    }

    static String storeResponseOptions(Iterable<String> options) {
        List<String> encoded = new ArrayList<String>();
        for (String option : options) {
            encoded.add(option.replaceAll(",", "\\\\\\\\,"));
        }
        return join(encoded, ",");
    }

    static List<String> parseResponseOptions(String options) {
        List<String> parsed = new ArrayList<String>();
        for (String option : options.split("\\s*(?<!\\\\),\\s*")) {
            parsed.add(option.replaceAll("\\\\\\\\,", ","));
        }
        return Collections.unmodifiableList(parsed);
    }

    private static String join(List<String> list, String delim) {

        StringBuilder sb = new StringBuilder();

        String loopDelim = "";

        for(String s : list) {

            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }

        return sb.toString();
    }

    /**
     * The unique label or variable name for the data element.
     * The field is not displayed as part of the CRF but can be viewed as part of the CRF and Item Metadata, and is shown
     * in the Discrepancy Notes and Rules modules.
     *
     * This field is case-sensitive. Items with names "item1" and "Item1" will be treated as different items. This can
     * cause issues with many data analysis tools and should be avoided in most cases.
     *
     * For Enterprise customers interested in using Datamart:
     * Please note that Datamart treat items in case-insensitive manner. Please treat all ITEM_NAMES as case-insensitive.
     * Also for use with Datamart, Postgres has a list of reserved words and special characters which should not be used
     * as item names.
     *
     * This field should be used at all times.
     *
     * ITEM_NAME will be used to form the OID and the variable name when exporting data from OpenClinica.
     * Brevity is recommended for the value as it will be used to generate the unique OC_OID.
     *
     * Re-use of the same ITEM_NAME across CRF Versions indicates the variable is the same item. Once created, an item
     * name cannot be modified within the CRF. See "CRF Versioning" and "Scope of CRFs and Items" in this document
     * for more detail.
     */
    @NotNull @Size(min = 1, max = 255) @Pattern(regexp = ALPHA_NUMERIC_ENGLISH_NO_SPACES_PATTERN) private String name;

    /**
     * The description or definition of the item. Should give an explanation of the data element and the value(s) it
     * captures. It is not shown on the CRF but is in the data dictionary.
     *
     * This field must be used at all times.  Provide a description that will help explain what the variable means
     * and what values it is collecting.
     *
     * For example, if the variable were looking to collect HEIGHT, the DESCRIPTION_LABEL would be "This variable
     * collects the height of the subject.  It captures the value in inches."
     *
     * This field should not be changed in any subsequent versions of the CRF. If you do change it  and you are the
     * owner of the CRF the DESCRIPTION_LABEL attribute for this item will be changed for all versions of the CRF.
     */
    @NotNull @Size(min = 1, max = 4000) private String descriptionLabel;

    /**
     * Descriptive text that appears to the left of the input on the CRF. Often phrased in the form of a question, or
     * descriptive label for the form field input.
     * 
     * HTML elements are allowed; however, only a limited subset of tags is officially supported (bold &lt;b&gt;,
     * italics &lt;i&gt;, underline &lt;u&gt;, superscript &lt;sup&gt;, subscript &lt;sub&gt;, line break &lt;br/&gt;,
     * link &lt;a href=""&gt;, image &lt;img src=""&gt;).
     * 
     * This field should be used as a way of describing the expected input to users entering or reviewing CRF data. 
     * The value of LEFT_ITEM_TEXT is displayed to the left of the form input. 
     * The text wraps after the first 20 characters.
     * 
     * An example question would be "What is the subject's height?"  Or, a simple one word "Height" suffices as well.
     * 
     * If the item is part of a repeating group (GRID), the LEFT_ITEM_TEXT is displayed as a column header above 
     * the field and not be displayed to the left of the item.
     * 
     */
    @Size(max = 2000) private String leftItemText;

    /**
     * Used to define the type of values being collected.  It appears to the right of the input field on the CRF.
     *
     * If you are collecting data in Inches, this field can specify your units as Inches, IN, or in.
     * This field should not be changed in any subsequent versions of the CRF. If you do change it and you are the owner
     * of the CRF and no data have been entered for this item, the UNITS attribute for this item will be changed for all
     * versions of the CRF.
     *
     * There are no edit checks associated specifically with units. This will appear as text to right of the input field
     * and will be displayed between parenthesis.
     *
     * If you are exporting to CDISC ODM XML format, this will appear in the metadata as measurement units.
     */
    @Size(max = 64) private String units;

    /**
     * Descriptive text that appears to the right of the form input on the CRF, and to the right of any UNITS that are
     * specified too. Often phrased in the form of a question, or supporting instructions for the form field input.
     *
     * HTML elements are allowed; however, only a limited subset of tags is officially supported (bold &lt;b&gt;,
     * italics &lt;i&gt;, underline &lt;u&gt;, superscript &lt;sup&gt;, subscript &lt;sub&gt;, line break &lt;br/&gt;,
     * link &lt;a href=""&gt;, image &lt;img src=""&gt;).
     *
     * This field can be used as a way of describing the expected input to users entering or for field-specific
     * instructions. The value of RIGHT_ITEM_TEXT is displayed to the right of the form input. The text wraps after
     * the first 20 characters.
     *
     * An example of use of right item text is "If other, please specify".
     *
     * If the item is part of a repeating group (GRID), the RIGHT_ITEM_TEXT will be ignored and never displayed.
     *
     */
    @Size(max = 2000) private String rightItemText;

    /**
     * Logically organizes the items that should be together on a section.
     *
     * The items in a given section are displayed on a single web page when the user is entering data, and appear
     * in the order they are entered in the Template.
     *
     * Every item in the worksheet must be assigned to a section of the CRF.
     *
     * For example, all of the information collected as part of a physical exam like Height, Weight, Blood Pressure,
     * and Heart Rate should be on the same section.
     */
    @NotNull @Valid private Section section;

    /**
     * Assigns items to an item group.  If the group is repeating, the items need to have the same SECTION_LABEL as
     * all other items in the group and must be consecutively defined in the ITEMS worksheet.
     *
     * Repeating items are displayed on a single row with the LEFT_ITEM_TEXT (if any exists) as a column header.
     *
     * This field should be used to identify whether an item belongs to an item group defined in the GROUPS worksheet.
     *
     * If the group is a repeating group (GRID layout), each item in the group is displayed as a column in the grid.
     * Too many items in a group, or use of long LEFT_ITEM_TEXT values, will make the grid extremely wide and force
     * the data entry user to scroll the page to the right to complete data entry.
     *
     * For non-repeating items, specify a group label to be used to logically assemble related items together for easier
     * data analysis.
     *
     * OpenClinica 3.1.2 and previous versions allowed items to be moved from one item group to another between versions
     * (i.e. UNGROUPED items could later be grouped). While  OpenClinica allowed this functionality, ODM does not
     * support this type of structure change between different CRF versions. As a result, these types of structural
     * changes could break extracts which contain the effected CRF. A new column has been introduced to View CRF page
     * to allow a user to verify CRF integrity. The new table, called 'Items' gives a list of items in a CRF where
     * the last two columns ('Version(s)' and 'Integrity Check') provide information about which version(s) the item
     * belongs to and if the item passes the integrity check (verifying that the item has not been assigned to more than
     * one item group).
     *
     * OpenClinica 3.1.3 and future versions will not allow items to be assigned to different item groups between versions.
     *
     */
    @Valid private Group group;

    /**
     * Contains text that used as a header for a particular item. Using this field will break up the items with
     * a distinct line between the header information and the next set of items. The text is bolded to call greater
     * attention to it.
     *
     * HTML elements are allowed; however, only a limited subset of tags is officially supported (bold &lt;b&gt;,
     * italics &lt;i&gt;, underline &lt;u&gt;, superscript &lt;sup&gt;, subscript &lt;sub&gt;, line break &lt;br/&gt;,
     * link &lt;a href=""&gt;, image &lt;img src=""&gt;).
     *
     * This field can be used as a replacement for left and right item text or as a replacement for instructions.
     * It allows a greater number of characters, along with bolding the text, to get the data entry person's attention.
     */
    @Size(max = 2000) private String header;


    /**
     * This field can contain text that will be used underneath the HEADER, or independently of a HEADER being provided.
     * The text will be separated by a line and have a grey background.
     *
     * HTML elements are allowed; however, only a limited subset of tags is officially supported (bold &lt;b&gt;,
     * italics &lt;i&gt;, underline &lt;u&gt;, superscript &lt;sup&gt;, subscript &lt;sub&gt;, line break &lt;br/&gt;,
     * link &lt;a href=""&gt;, image &lt;img src=""&gt;).
     *
     * This field can be used as a replacement or augmentation for left and right item text or as a
     * replacement/augmentation for section/group instructions.  It allows a greater number of characters, along with
     * providing a grey background to the text in order to get the data entry user's attention.
     */
    @Size(max = 240) private String subheader;

    /**
     * Assigns items to an item group.  If the group is repeating, the items need to have the same SECTION_LABEL as all
     * other items in the group and must be consecutively defined in the ITEMS worksheet.  Repeating items are displayed
     * on a single row with the LEFT_ITEM_TEXT (if any exists) as a column header.
     *
     * This is to be used with only non-repeating items and controls display of multiple items on a single row.
     * If you set the column to 3 for an item, the previous two items in the worksheet should have COLUMN_NUMBERS
     * of 1 and 2.  Otherwise, it will just be applied to the first column.
     *
     * Use of COLUMN_NUMBERS greater than 3 is not recommended due to typical screen width limitations.
     */
    @Min(1) private Integer columnNumber;

    /**
     * The page number on which the section begins. If using paper source documents and have a multi-page CRF,
     * put in the printed page number.
     *
     * For the most part, this field is only used in studies collecting data on multi-page paper forms and then having
     * the data keyed in at a central location performing double data entry.
     */
    @Size(max = 5) @Pattern(regexp = ValidationConstants.ALPHA_NUMERIC_PATTERN) private String pageNumber;

    /**
     * This field is used to specify an identifier for each item or question in the Items worksheet.  It appears to the
     * left of the LEFT_ITEM_TEXT field, or if that field was left blank, to the left of the form input.
     *
     * This field allows you to specify questions as 1, 2, 2a etc. in a field.
     */
    @Size(max = 20) @Pattern(regexp = ValidationConstants.ALPHA_NUMERIC_PATTERN) private String questionNumber;

    /**
     * The types of responses are based on standard HTML elements web browsers can display in a form. Allowed use of
     * the available RESPONSE_TYPEs depends on the item DATA_TYPE and use of Response Sets.
     */
    @NotNull private final ResponseType responseType;

    /**
     * Create a custom label associated with a response set. This label must be defined once and may be reused by other i
     * tems with the same responses (e.g. Yes, No) and values.
     *
     * In order to facilitate the creation of a CRF, unnecessary duplication of RESPONSE_OPTIONS_TEXT and
     * RESPONSE_VALUES_OR_CALCULATIONS values can be mitigated by the RESPONSE_LABEL.
     *
     * If the same options and values are going to be used for multiple items like Yes, No and 1,2, provide
     * the information once and enter a unique response label.  This label can be used throughout the rest of the Items
     * worksheet so other items will use the exact same options and values. If a RESPONSE_LABEL is reused within a CRF,
     * the RESPONSE_OPTIONS_TEXT and RESPONSE_VALUES_OR_CALCULATIONS must be left blank or exactly match the values of
     * the original RESPONSE_LABEL in the CRF.
     */
    @Size(max = 80) @Pattern(regexp = ValidationConstants.ALPHA_NUMERIC_PATTERN) private String responseLabel;

    /**
     * A comma delimited string of values that will be used as the options to be chosen by a data entry person when
     * they are entering data in a CRF.
     *
     * This field is only used for checkbox, multi-select, radio and single-select fields.  This will be the text
     * displayed to the data entry person, which they will choose for each item.  If the options themselves contain
     * commas (,) you must escape the commas with a /
     *
     */
    @Size(max = 4000) private String responseOptionsText;

    /**
     * If the field is not a calculation or group-calculation, this will be a comma-delimited string of values that will
     * be used as the values saved to the database when a user chooses the corresponding RESPONSE_OPTIONS_TEXT.
     *
     * If this is a calculation or group-calculation field, it will be an expression that takes the inputs of other
     * items in the Items worksheet that are of INT or REAL data type to calculate a value.
     *
     * For checkbox, multi-select, radio and single-select fields, this will be the values that correspond to a
     * RESPONSE_OPTIONS_TEXT.  The number of options and values must match exactly or the CRF will be rejected when it
     * is uploaded into OpenClinica.
     *
     * The following calculations are allowed in this field if the RESPONSE_TYPE is calculation, sum(), avg(), min(),
     * max(), median(), stdev(), pow(), and decode().
     *
     * Cumulative calculations on a group of repeating items must be of type group-calculation. Only cumulative
     * calculations on the entire set of repeating items are allowed. The allowed functions are sum(), avg(), min(),
     * max(), median(), and stdev().
     * For example, in an invoice with a repeating group of line items, the calculation for a total price would be
     * the group-calculation "func: (sum (LINE_ITEM_PRICE))".
     *
     * Instant calculation fields should use this field to define the onchange() function with arguments of an item name
     * (the trigger item) and value.
     *
     */
    @Size(max = 4000) private String responseValuesOrCalculations;

    /**
     * The layout of the options for radio and checkbox fields.
     *
     * The options can be left to right, or top to bottom depending on the value specified in the Items worksheet.
     *
     * Leaving the field blank and selecting Vertical display the items in a single column from top to bottom.
     * Choosing Horizontal will put the items in a single row, left to right.
     *
     */
    private ResponseLayout responseLayout;

    /**
     * Default text for RESPONSE_OPTIONS_TEXT.
     *
     * This field allows the user to specify a default value that will appear in the CRF section the first time the user
     * accesses the form.  For single-select default value does not have to be part of the response set and can be
     * instructive text if need be.  It will be interpreted as a blank value if the user does not choose anything.
     *
     * Default values can be used for the following RESPONSE_TYPEs:
     * <ul>
     * <li>TEXT</li>
     * <li>TEXTAREA</li>
     * <li>SINGLE-SELCT</li>
     * <li>MULTI-SELECT</li>
     * <li>CHECKBOX</li>
     * </ul>
     *
     * Default values can not be used for the following RESPONSE_TYPEs (CRF will be rejected on upload):
     * <ul>
     * <li>CALCULATION</li>
     * <li>GROUP_CALCULATION</li>
     * <li>FILE</li>
     * <li>INSTANT_CALCULATION</li>
     * <li>RADIO</li>
     * </ul>
     *
     * Be careful in using this field because if the default value corresponds to an option in the response set, it will
     * be saved to the database even if the user does not select it.
     */
    @Size(max = 4000) private String defaultValue;

    /**
     * The data type is the format the value should be supplied in.
     */
    @NotNull private DataType dataType;

    /**
     * Specify the width (the length of the field) and the number of decimal places to use for the field.
     * If provided must be in the form w(d) as follows:
     *
     * w - integer from 1 to 26, or literal 'w' if INT or REAL.  If ST, from 1 to 4000 is allowed.
     *
     * d - literal 'd'. if the item has DATA_TYPE of 'REAL', may also be an integer from 1 to 20. d cannot be larger than w
     *
     *
     * Defines the width (the maximum allowed length of the field) and the number of decimal places to use for the field
     * in the form w(d).
     *
     * The first input defines the width of the field. The second input specifies the number of decimal places for
     * the field, if the item has a DATA_TYPE of 'REAL'.
     *
     * The WIDTH_DECIMAL attribute should only be used for items with the ST, INT or REAL data types. The width
     * attribute specifies the length of the field treated as a string, so even if the item is of type INT or REAL,
     * leading/trailing zeroes and decimal points count towards the width.
     *
     * For items of type REAL, evaluation of the width occurs prior to evaluation of the decimal, so values exceeding
     * the specified or system default width will be rejected even if they could be rounded to a length within
     * the width limit.
     *
     * Examples.:
     * DATA_TYPE 'REAL', WIDTH_DECIMAL 5(1) - Allows a maximum of 5 characters with only 1 decimal place. OpenClinica
     * will accept 12345 and 1234., 123.4, or 12.30  but will not accept 012345 or 123456.
     *
     * Inputs such as 12.345 or 1234.5678 or 012345 or 12.300 will be allowed and rounded.
     *
     * REAL w(4) -Allows up to OpenClinica's maximum length for an item of ST, INT, or REAL (26 characters), with
     * any decimal in excess of 1/10000th rounded to the 4th decimal place.
     *
     * REAL 20(d) -Allows a maximum length of 20 and decimal length of 4 (the default in OpenClinica).
     *
     * ST 20(d) or INT 20(d) - Allows a maximum length of 20 characters.
     *
     * If the DATA_TYPE of the item is DATE, PDATE, or FILE, the WIDTH_DECIMAL attribute should be left blank.
     *
     * Please be advised that OpenClinica is not tuned to process very large REAL numbers or numbers with many digits
     * after decimal point. So, numbers like 1234.123456789012345589 may not be validated properly to their format.
     * For more complex scenarios where precision above 20 digits is required, it may be better to use a regular
     * expression to verify the input.
     *
     */
    @Pattern(regexp = WIDTH_DECIMAL_PATTERN) private String widthDecimal;

    /**
     * Specify a validation expression to run an edit check on this item at the point of data entry.
     *
     * The validation will run when the user hits 'save'. If the user has entered data, which satisfy the validation
     * expression, data will save normally. If the value entered does not meet the requirements of the validation,
     * an error message will appear (i.e., the VALIDATION_ERROR_MESSAGE) and the user will need to correct the value
     * or enter a discrepancy note to continue. The validation should be of the format "expressionType: expression".
     * Must be between 1 and 1000 characters and is an optional field.
     *
     * regexp:
     *
     * This Supports Java-style regular expressions (similar to Perl). For more information,
     * see http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
     *
     * Examples:
     * This example requires a three-letter string (all uppercase)
     * regexp: /regular expression/ = regexp: /[A-Z]{3}/
     *
     * func:
     *
     * Supports built-in library of basic integer functions. Currently supported functions include:
     * (1) greater than - gt(int) or gt(real)
     * (2) less than - lt(int) or lt(real)
     * (3) range - range(int1, int2) or range(real1, real2)
     * (4) gte(int) or gte(real)
     * (5) lte(int) or lte(real)
     * (6) ne(int) or ne(real)
     * (7) eq(int) or eq(real)
     *
     * Examples:
     * This example requires a number between 1 and 10
     * func: func(args) = func: range(1, 10)
     */
    @Size(min = 1, max = 1000) private String validation;

    /**
     * Defines an error message provided to on the data entry screen when a user enters data that does not meet
     * the VALIDATION.
     *
     * Must be used when a VALIDATION is specified and it should be clear to the data entry person what the problem is.
     * If there is a VALIDATION stating the number must be between 1-10, write that message in this field for the user
     * to see if they enter 11 or 0.
     */
    @Size(min = 1, max = 255) private String validationErrorMessage;

    /**
     * Signifies whether this item would be considered Protected Health Information.
     *
     * Leaving the field blank or selecting 0 means the item would not be considered Protected Health Information.
     * This flag does not do anything to mask the data or prevent people from seeing it.
     * The field is used as a label only.
     *
     * When creating a data set, this label will show in the metadata and the user could choose to include this item
     * in the dataset (create dataset step) or not based on this label.
     *
     * This field should not be changed in any subsequent versions of the CRF. If you do change it and you are
     * an owner of the CRF the PHI attribute for this item will be changed for all versions of the CRF.
     */
    @Min(0) @Max(1) private Integer phi;

    /**
     * This field determines whether the user must provide a value for it before saving the section the item appears in.
     *
     * Leaving the field blank or selecting 0 means the item would be optional so the data entry person does not have to
     * provide a value for it.  If 1 is selected, the data entry person must provide a value, or enter a discrepancy
     * note explaining why the field is left blank. This can be used for any RESPONSE_TYPE
     *
     */
    @Min(0) @Max(1) private Integer required;

    /**
     * Used in conjunction with Dynamics in Rules or SIMPLE_CONDITIONAL_DISPLAY. If set to HIDE, the item will not
     * appear in the CRF when a user is entering data unless certain conditions are met. The conditions for display are
     * specified with a Rule using the ShowAction, or via SIMPLE_CONDITIONAL_DISPLAY. If left blank, the value defaults
     * to SHOW.
     *
     * If you would like to design skip patterns and dynamic logic for a single item, set the display status to HIDE.
     *
     * When the form is accessed for data entry, the item will initially be hidden from view from the user.  With Rules,
     * another value can trigger the group of items to be shown instead of hidden.
     *
     * Instead of Rules, you can also use the SIMPLE_CONDITIONAL_DISPLAY field to decide when this item should be shown.
     * SIMPLE_CONDITIONAL_DISPLAY only works with items set to HIDE.
     */
    @NotNull private DisplayStatus displayStatus;

    /**
     * Contains 3 parts, all separated by a comma:  ITEM_NAME, RESPONSE_VALUE, Message.
     *
     * ITEM_NAME - The item name of the field determining whether this hidden item becomes shown.
     * RESPONSE_VALUE - The value of the ITEM_NAME that will trigger this hidden item to be shown
     * Message - A validation message that will be displayed if this item has a value but should not be shown anymore.
     *
     * Simple Conditional Display works with items that have a defined response set (radio, checkbox, multi-select and
     * single-select fields).  The hidden item can be of any response type.
     *
     * SIMPLE_CONDITIONAL_DISPLAY (SCD) has an effect only when ITEM_DISPLAY_STATUS (IDS) of the item is set to HIDE.
     * Several levels of hierarchy of Simple Conditional fields can be nested hierarchically. The items must be in the
     * same section of the CRF
     *
     * For example, assume there is a SEX item with response options of Male, Female, and response values of 1,2.
     * If the user chooses Female option, additional questions about pregnancy are asked. If Male is chosen,
     * these questions are hidden. However, if the user chooses Female, fills in pregnancy data and after that gets back
     * to the SEX item and switches the answer to Male, the items about pregnancy will remain on the screen (not hidden).
     * The user can delete pregnancy answers and in that case the UI items will get hidden.
     *
     * Note that the database gets updated only on SAVE. In the above example the system will allow saving
     * "inconsistent" data (SEX = Male, but pregnancy items filled), but it is up to a user to create discrepancy
     * fields for them explaining the situation.
     *
     * Note that radio button controls cannot be deselected, meaning there is no way to delete it's value once it has
     * been selected.
     *
     */
    private String simpleConditionalDisplay;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescriptionLabel() {
        return descriptionLabel;
    }

    @Override
    public void setDescriptionLabel(String descriptionLabel) {
        this.descriptionLabel = descriptionLabel;
    }

    @Override
    public String getLeftItemText() {
        return leftItemText;
    }

    @Override
    public void setLeftItemText(String leftItemText) {
        this.leftItemText = leftItemText;
    }

    @Override
    public String getUnits() {
        return units;
    }

    @Override
    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String getRightItemText() {
        return rightItemText;
    }

    @Override
    public void setRightItemText(String rightItemText) {
        this.rightItemText = rightItemText;
    }

    @Override
    public Section getSection() {
        return section;
    }

    void setSection(Section section) {
        this.section = section;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String getSubheader() {
        return subheader;
    }

    @Override
    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }

    @Override
    public Integer getColumnNumber() {
        return columnNumber;
    }

    @Override
    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Override
    public String getPageNumber() {
        return pageNumber;
    }

    @Override
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public ResponseType getResponseType() {
        return responseType;
    }

    public String getResponseLabel() {
        return responseLabel;
    }

    public void setResponseLabel(String responseLabel) {
        this.responseLabel = responseLabel;
    }

    public String getResponseOptionsText() {
        return responseOptionsText;
    }

    public String getResponseValuesOrCalculations() {
        return responseValuesOrCalculations;
    }

    @Override
    public void setResponseOptions(List<ResponseOption> options) {
        List<String> texts = new ArrayList<String>();
        List<String> values = new ArrayList<String>();

        for (ResponseOption option : options) {
            option.setItem(this);
            texts.add(option.getText());
            values.add(option.getValue());
        }

        if (this.responseLabel == null && this.name != null) {
            setResponseLabel(this.name + RESPONSE_LABEL_SUFFIX);
        }
        this.responseOptionsText = storeResponseOptions(texts);
        this.responseValuesOrCalculations = storeResponseOptions(values);
    }

    @Override
    public List<ResponseOption> getResponseOptions() {
        List<String> texts = parseResponseOptions(this.responseOptionsText);
        List<String> values = parseResponseOptions(this.responseValuesOrCalculations);
        List<ResponseOption> options = new ArrayList<ResponseOption>();

        for (int i = 0; i < texts.size(); i++) {
            options.add(new ResponseOption(this, values.get(i), texts.get(i)));
        }

        return options;
    }

    @Override
    public ResponseLayout getResponseLayout() {
        return responseLayout;
    }

    @Override
    public void setResponseLayout(ResponseLayout responseLayout) {
        this.responseLayout = responseLayout;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getWidthDecimal() {
        return widthDecimal;
    }

    @Override
    public void setWidthDecimal(String widthDecimal) {
        this.widthDecimal = widthDecimal;
    }

    public String getValidation() {
        return validation;
    }

    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }

    @Override
    public Integer getPhi() {
        return phi;
    }

    @Override
    public void setPhi(Integer phi) {
        this.phi = phi;
    }

    @Override
    public Integer getRequired() {
        return required;
    }

    @Override
    public void setRequired(Integer required) {
        this.required = required;
    }

    @Override
    public DisplayStatus getDisplayStatus() {
        return displayStatus;
    }

    @Override
    public void setDisplayStatus(DisplayStatus displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getSimpleConditionalDisplay() {
        return simpleConditionalDisplay;
    }

    @Override
    public ConditionalDisplay getConditionalDisplay() {
        if (simpleConditionalDisplay == null) {
            return null;
        }
        if (section == null) {
            return null;
        }
        List<String> parts = parseResponseOptions(simpleConditionalDisplay);

        Item item = getSection().getItems().get(parts.get(0));

        ResponseOption option = null;
        for (ResponseOption responseOption : item.getResponseOptions()) {
            if (parts.get(1).equals(responseOption.getValue())) {
                option = responseOption;
                break;
            }
        }

        return new ConditionalDisplay(option, parts.get(2));
    }

    @Override
    public void setConditionalDisplay(ConditionalDisplay conditionalDisplay) {
        List<String> values = new ArrayList<String>();
        values.add(conditionalDisplay.getResponse().getItem().getName());
        values.add(conditionalDisplay.getResponse().getValue());
        values.add(conditionalDisplay.getMessage());
        this.simpleConditionalDisplay = storeResponseOptions(values);
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public void setCalculation(String calculation) {
        if (!Arrays.asList(ResponseType.CALCULATION, ResponseType.GROUP_CALCULATION, ResponseType.INSTANT_CALCULATION).contains(responseType)) {
            throw new IllegalStateException("Cannot set calculation for non-calculation response types.");
        }
        if (this.responseLabel == null && this.name != null) {
            setResponseLabel(this.name + RESPONSE_LABEL_SUFFIX);
        }
        this.responseOptionsText = CALCULATION_RESPONSE_OPTIONS_TEXT;
        this.responseValuesOrCalculations = calculation;
    }

    @Override
    public String getCalculation() {
        return this.responseValuesOrCalculations;
    }

    @Override
    public ValidationExpression getValidationExpression() {
        return new ValidationExpression(validation, validationErrorMessage);
    }

    @Override
    public void setValidationExpression(ValidationExpression expression) {
        this.validation = expression.getExpression();
        this.validationErrorMessage = expression.getMessage();
    }
}
