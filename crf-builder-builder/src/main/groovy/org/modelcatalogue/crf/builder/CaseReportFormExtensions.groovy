package org.modelcatalogue.crf.builder

import org.modelcatalogue.crf.builder.util.ConditionDelegate
import org.modelcatalogue.crf.builder.util.RowDelegate
import org.modelcatalogue.crf.builder.util.ValidationFunctionDelegate
import org.modelcatalogue.crf.model.Calculation
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.Checkbox
import org.modelcatalogue.crf.model.ConditionalDisplay
import org.modelcatalogue.crf.model.DataType
import org.modelcatalogue.crf.model.DisplayStatus
import org.modelcatalogue.crf.model.GenericItem
import org.modelcatalogue.crf.model.GridGroup
import org.modelcatalogue.crf.model.Group
import org.modelcatalogue.crf.model.GroupCalculation
import org.modelcatalogue.crf.model.HasCalculation
import org.modelcatalogue.crf.model.HasDefaultValue
import org.modelcatalogue.crf.model.HasDisplayStatus
import org.modelcatalogue.crf.model.HasPageNumber
import org.modelcatalogue.crf.model.HasResponseLayout
import org.modelcatalogue.crf.model.HasResponseOptions
import org.modelcatalogue.crf.model.InstantCalculation
import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ItemContainer
import org.modelcatalogue.crf.model.MinimalItem
import org.modelcatalogue.crf.model.MultiSelect
import org.modelcatalogue.crf.model.Radio
import org.modelcatalogue.crf.model.ResponseLayout
import org.modelcatalogue.crf.model.ResponseOption
import org.modelcatalogue.crf.model.Section
import org.modelcatalogue.crf.model.SingleSelect
import org.modelcatalogue.crf.model.Text
import org.modelcatalogue.crf.model.Textarea
import org.modelcatalogue.crf.model.File as ModelFile
import org.modelcatalogue.crf.model.validation.ValidationExpression

class CaseReportFormExtensions {

    // form extensions

    /**
     * Defines the version of the CRF as it will be displayed in the OpenClinica user interface.
     *
     * You cannot provide a value that has already been used in the OpenClinica instance unless it has not been assigned
     * to an event definition yet.  If a particular CRF version has not been used in an event definition, you may
     * overwrite it.
     *
     * If this is a new version of a CRF that already exists, the CRF_NAME field must match the value of the form
     * already in OpenClinica.
     *
     * A new version of a CRF would be needed due to a protocol change, adding or removing an item from a CRF, or
     * changing some of the questions.
     * @param form current form
     * @param version the version of the form which has to be unique alphanumeric string between 1 and 255 characters
     * @see CaseReportForm#version
     */
    static void version(CaseReportForm form, String version) {
        form.version = normalize version
    }

    /**
     * This field is used for informational purposes to keep track of what this version of the CRF was created for.
     *
     * This information appears as part of the CRF Metadata when the user clicks on View (original). This information
     * is not displayed during data entry.
     *
     * @param form current form
     * @param versionDescription the version description of the form which has to be alphanumeric string between 1 and 4000 characters
     * @see CaseReportForm#versionDescription
     */
    static void versionDescription(CaseReportForm form, String versionDescription) {
        form.versionDescription = normalize versionDescription
    }

    /**
     * This field is used to keep track of the revisions you made to this particular CRF.
     *
     * This information appears as part of the CRF Metadata when the user clicks on View (original). This information is
     * not displayed during data entry.
     *
     * If this is the first version of the CRF, you can simply state this is a brand new form.  Going forward, as you
     * make changes and update the versions you can provide information on what is different between the first version
     * and each subsequent version.
     *
     * @param form current form
     * @param revisionNotes the revision notes of the form which has to be alphanumeric string between 1 and 255 characters
     * @see CaseReportForm#revisionNotes
     */
    static void revisionNotes(CaseReportForm form, String revisionNotes) {
        form.revisionNotes = normalize revisionNotes
    }

    /**
     * Creates new section with given label in current form.
     *
     * Calling with the same label will cause reconfiguring already existing section.
     *
     * @param label unique label of the section which can contain 1 to 255 alphanumeric characters and dashes
     * @param closure configuration closure using section as a delegate
     * @return new or existing section with given label
     */
    static Section section(CaseReportForm form, String label, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Section) Closure closure) {
        Section section = form.section(label)
        section.with closure
        section
    }


    // section extensions

    /**
     * The value in this field will be displayed at the top of each page when a user is performing data entry, as well
     * as in the tabs and drop down list used to navigate between sections in a CRF. It does not have to be unique but
     * should be a readable value that makes sense to people entering data.  An example would be 'Inclusion Criteria'
     *
     * This field must be used at all times.  If a CRF does not have a value for SECTION_TITLE the form will be rejected
     * at upload time.
     *
     * Long section titles may not display well.
     *
     * @param section current section
     * @param title section title which can contain 1 to 2000 characters of any type.
     */
    static void title(Section section, String title) {
        section.title = normalize title
    }

    /**
     * A sub-title shown under the section title.
     *
     * HTML elements are supported for this field.
     * @param section current section
     * @param subtitle subtitle of the section which can contain up to 2000 characters of any type
     */
    static void subtitle(Section section, String subtitle) {
        section.subtitle = normalize subtitle
    }

    /**
     * Instructions at the top of the section (under the subtitle) that explains to the data entry person what to do on
     * this section of the form.
     *
     * HTML elements are supported for this field.
     *
     * This field should be used if there are particular data entry instructions that should be conveyed or followed
     * to users.
     *
     * @param section current section
     * @param instructions instructions of the section which can contain up to 2000 characters of any type
     */
    static void instructions(Section section, String instructions) {
        section.instructions = normalize instructions
    }

    /**
     * Creates new non-repeating group with given label or returns the existing one.
     * @param label label of the new non-repeating group.
     * @param closure configuration closure which is delegate to the group
     * @return new non-repeating group with given label or existing one
     */
    static Group group(Section section, String label, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Group) Closure closure) {
        Group group = section.group(label)
        group.with closure
        group
    }

    /**
     * Creates new repeating grid group with given label or returns exsiting one.
     * @param label label of the new repeating grid group.
     * @param section current section
     * @param closure configuration closure which is delegated to the grid group
     * @return new repeating grid group with given label.
     */
    static GridGroup grid(Section section, String label, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=GridGroup) Closure closure) {
        GridGroup group = section.grid(label)
        group.with closure
        group
    }

    // displaying

    /**
     * Hide current section or item.
     * @param hasDisplayStatus current section or item
     * @param hide whether the current section or item should be hidden
     */
    static void hide(HasDisplayStatus hasDisplayStatus, boolean hide) {
        hasDisplayStatus.displayStatus = hide ? DisplayStatus.HIDE : DisplayStatus.SHOW
    }

    // having page number extensions

    /**
     * The page number on which the section begins or where the item is placed.
     *
     * If using paper source documents and have a multi-page CRF, put in the printed page number.
     *
     * For the most part, this field is only used in studies collecting data on multi-page paper forms and then having
     * the data keyed in at a central location performing double data entry.
     * @param section current section or item
     * @param pageNumber page number with size up to 5 characters
     */
    static void pageNumber(HasPageNumber section, String pageNumber) {
        section.pageNumber = normalize pageNumber
    }


    // checkboxes and radios
    /**
     * The layout of the options for radio and checkbox fields.
     *
     * The options can be left to right, or top to bottom depending on the value specified in the Items worksheet.
     *
     * Leaving the field blank and selecting Vertical display the items in a single column from top to bottom.
     * Choosing Horizontal will put the items in a single row, left to right.
     *
     * @param item current item
     * @param layout either <code>horizontal</code> or <code>vertical</code>
     */
    static void layout(HasResponseLayout item, ResponseLayout layout) {
        item.responseLayout = layout
    }

    /**
     * Default text for item.
     *
     * This field allows the user to specify a default value that will appear in the CRF section the first time the user
     * accesses the form.  For single-select default value does not have to be part of the response set and can be
     * instructive text if need be.  It will be interpreted as a blank value if the user does not choose anything.
     *
     * Be careful in using this field because if the default value corresponds to an option in the response set, it will
     * be saved to the database even if the user does not select it.
     *
     * @param item current item
     * @param value default value for the item
     */
    static void value(HasDefaultValue item, Object value) {
        item.defaultValue = value?.toString()
    }

    // validation
    /**
     * Sets the regular expression for the item.
     *
     * @param item current item
     * @param regexp regular expression which has to be met
     * @param errorMessage error message displayed when the regular expression is not met
     */
    static void regexp(GenericItem item, String regexp, String errorMessage) {
        item.validationExpression = new ValidationExpression("regexp: /$regexp/", errorMessage)
    }

    /**
     * Sets the validation function for the item.
     *
     * Example:
     * <pre>
     * validate('Value must be between 1 and 10') {
     *     range(1,10)
     * }
     * </pre>
     * @param item current item
     * @param errorMessage error message displayed when the function returns false
     * @param closure configuration function delegated to ValidationFunctionDelegate
     */
    static void validate(GenericItem item, String errorMessage, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=ValidationFunctionDelegate) Closure<String> closure) {
        item.validationExpression = new ValidationExpression(new ValidationFunctionDelegate().with(closure), errorMessage)
    }


    // responses
    /**
     * Sets the options for the responses. The keys are the text displayed, the values are the values stored in
     * database
     * @param item current item
     * @param values possible options for current item with text displayed as keys and values as values
     */
    static void options(HasResponseOptions item, Map<String, Object> values) {
        item.responseOptions = values.collect { String key, Object value -> new ResponseOption(key, value?.toString()) }
    }

    // simple display status
    /**
     * Sets the condition to show the display. It also marks the item as hidden by default as expected form the spec.
     *
     * Example:
     * <pre>
     * show {
     *     when 'exam' is 2 otherwise 'You should not fill comment when exam results are normal'
     * }
     * </pre>
     * @param item current item
     * @param closure configuration closure delegated to ConditionDelegate
     */
    static void show(MinimalItem item, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=ConditionDelegate) Closure closure) {
        ConditionDelegate delegate = new ConditionDelegate()
        delegate.with closure

        Item refItem = item.getSection().getCaseReportForm().findItem(delegate.item)

        if (!refItem) {
            throw new IllegalArgumentException("Item '$delegate.item' not found for the form!")
        }

        ResponseOption option = refItem.responseOptions.find { it.value == delegate.value?.toString() }

        if (!option) {
            throw new IllegalArgumentException("Item '$delegate.item' does not provides any option with value '$delegate.value'!")
        }

        item.displayStatus = DisplayStatus.HIDE
        item.conditionalDisplay = new ConditionalDisplay(option, delegate.stale)
    }

    // group extensions

    /**
     * The value is displayed above the GRID when a user is performing data entry.
     *
     * This value is like a title for the group.  An example of a GROUP_HEADER
     * would be "Medications Log."
     *
     * The field can be left blank if you do not want a title or header.  If the Layout is set to NON-REPEATING,
     * the value will be ignored and not displayed during data entry.
     *
     * @param gridGroup current grid
     * @param header
     */
    static void header(GridGroup gridGroup, String header) {
        gridGroup.header = normalize header
    }


    /**
     * The default (initial) number of repeats on the form should be provided here. If left blank, only one row of
     * information will be displayed by default.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     *
     * This field should be used to specify how many rows of data should exist for the item group upon initiation of
     * data entry, or in printing of a blank CRF from OpenClinica. If three rows of information, specify the number 3
     * in the field. When a user accesses the CRF, the row will be repeated 3 times by default.
     *
     * A user will be allowed to add more rows up to the GROUP_REPEAT_MAX and even remove some of the rows displayed
     * by default.
     *
     * @param gridGroup current grid
     * @param rows initial number of rows.
     */
    static void rows(GridGroup gridGroup, int rows) {
        gridGroup.repeatNum = rows
    }

    /**
     * The total number of rows a user will be allowed to repeat in the item group.  When left blank, the default number
     * of repeats is 40.
     *
     * Used only when the GROUP_LAYOUT is equal to GRID.
     *
     * This field should be used to restrict users to a certain number of repeats for the GRID.  However, this
     * restriction works only if data are entered through OpenClinica Web UI. If data are imported using
     * Task-> Import Data option or through web services, all rows of data in the import file will be allowed to import,
     * even if the rows of data in the import exceed the GROUP_REPEAT_MAX..
     *
     * If GROUP_REPEAT_MAX is less than GROUP_REPEAT_NUMBER group will have GROUP_REPEAT_MAX number of rows on initial
     * data entry displayed and no additional rows can be added.
     *
     * @param gridGroup current grid
     * @param maxNumberOfRows maximum number of rows
     */
    static void upto(GridGroup gridGroup, int maxNumberOfRows) {
        gridGroup.repeatMax = maxNumberOfRows
    }


    // item container (group or section) extensions

    /**
     * Finds or creates new text item with predefined type TEXT.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new text item or modified one if the item of the same name already exists within container
     */
    static Text text(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Text) Closure closure) {
        Text item = container.text(name)
        item.with closure
        item
    }

    /**
     * Finds or creates new textarea item with predefined type TEXTAREA.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new textare item or modified one if the item of the same name already exists within container
     */
    static Textarea textarea(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Textarea) Closure closure) {
        Textarea item = container.textarea(name)
        item.with closure
        item
    }

    /**
     * Mutates the current section to sort it's items by question numbers.
     */
    static void sort(Section section, Map<String, Item> items) {
        if (items != section.items) {
            throw new IllegalArgumentException("Only items from current section can be sorted")
        }
        section.sortItemsByQuestionNumber()
    }


    /**
     * Finds or creates new single select item with predefined type SINGLE_SELECT.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new single select item or modified one if the item of the same name already exists within container
     */
    static SingleSelect singleSelect(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=SingleSelect) Closure closure) {
        SingleSelect item = container.singleSelect(name)
        item.with closure
        item
    }


    /**
     * Finds or creates new radio item with predefined type RADIO.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new radio item or modified one if the item of the same name already exists within container
     */
    static Radio radio(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Radio) Closure closure) {
        Radio item = container.radio(name)
        item.with closure
        item
    }


    /**
     * Finds or creates new multi select item with predefined type MULTI_SELECT.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new text item or modified one if the item of the same name already exists within container
     */
    static MultiSelect multiSelect(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=MultiSelect) Closure closure) {
        MultiSelect item = container.multiSelect(name)
        item.with closure
        item
    }


    /**
     * Finds or creates new checkbox item with predefined type CHECKBOX.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new checkbox item or modified one if the item of the same name already exists within container
     */
    static Checkbox checkbox(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Checkbox) Closure closure) {
        Checkbox item = container.checkbox(name)
        item.with closure
        item
    }

    /**
     * Finds or creates new calculation item with predefined type CALCULATION.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new calculation item or modified one if the item of the same name already exists within container
     */
    static Calculation calculation(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=Calculation) Closure closure) {
        Calculation item = container.calculation(name)
        item.with closure
        item
    }

    /**
     * Finds or creates new group calculation item with predefined type GROUP_CALCULATION.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new group calculation item or modified one if the item of the same name already exists within container
     */
    static GroupCalculation groupCalculation(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=GroupCalculation) Closure closure) {
        GroupCalculation item = container.groupCalculation(name)
        item.with closure
        item
    }

    /**
     * Finds or creates new file item with predefined type FILE.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new file item or modified one if the item of the same name already exists within container
     */
    static ModelFile file(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=ModelFile) Closure closure) {
        ModelFile item = container.file(name)
        item.with closure
        item
    }

    /**
     * Finds or creates new instant calculation item with predefined type INSTANT_CALCULATION.
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new instant calculation item or modified one if the item of the same name already exists within container
     */
    static InstantCalculation instantCalculation(ItemContainer container, String name, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=InstantCalculation) Closure closure) {
        InstantCalculation item = container.instantCalculation(name)
        item.with closure
        item
    }

    /**
     * Places items next to each other.
     *
     * @param container current item container
     * @param closure configuration closure
     * @throws IllegalArgumentException if the current container is GridGroup as columnNumber is not supported
     * for such group.
     */
    static void row(ItemContainer container, @DelegatesTo(strategy=Closure.DELEGATE_FIRST, value=RowDelegate) Closure closure) {
        if (container instanceof GridGroup) {
            throw new IllegalArgumentException("Cannot put items into row in repeating group")
        }
        RowDelegate delegate = new RowDelegate(container)
        delegate.with closure
    }

    // item extensions

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
     *
     * @param item current item
     * @param description description text from 1 to 4000 characters
     */
    static void description(MinimalItem item, String description) {
        item.descriptionLabel = normalize description
    }

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
     * @param item current item
     * @param question question text from 1 to 2000 characters
     */
    static void question(MinimalItem item, String question) {
        item.leftItemText = normalize question
    }

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
     * @param item current item
     * @param instructions instructions text upto 2000 characters
     */
    static void instructions(MinimalItem item, String instructions) {
        if (item.group instanceof GridGroup) {
            throw new IllegalArgumentException("Instructions (right item text) does not make sense for grid items")
        }
        item.rightItemText = normalize instructions
    }

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
     *
     * @param item current item
     * @param units units text upto 64 characters
     *
     */
    static void units(MinimalItem item, String units) {
        item.units = normalize units
    }

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
     *
     * @param item current item
     * @param header header text upto 2000 characters
     */
    static void header(MinimalItem item, String header) {
        if (item.group instanceof GridGroup) {
            throw new IllegalArgumentException("Header does not make sense for grid items")
        }
        item.header = normalize header
    }

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
     *
     * @param item current item
     * @param subheader subheader text upto 240 characters
     */
    static void subheader(MinimalItem item, String subheader) {
        if (item.group instanceof GridGroup) {
            throw new IllegalArgumentException("Subheader does not make sense for grid items")
        }
        item.subheader = normalize subheader
    }

    /**
     * This field is used to specify an identifier for each item or question in the Items worksheet.  It appears to the
     * left of the LEFT_ITEM_TEXT field, or if that field was left blank, to the left of the form input.
     *
     * This field allows you to specify questions as 1, 2, 2a etc. in a field.
     *
     * @param item current item
     * @param questionNumber questionNumber text upto 20 characters, could be a number
     */
    static void questionNumber(MinimalItem item, Object questionNumber) {
        item.questionNumber = normalize questionNumber?.toString()
    }

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
     *
     * @param item current item
     * @param phi <code>true</code> if current item contains protected health information
     *
     */
    static void phi(MinimalItem item, boolean phi) {
        item.phi = phi ? 1 : 0
    }

    /**
     * This field determines whether the user must provide a value for it before saving the section the item appears in.
     *
     * Leaving the field blank or selecting 0 means the item would be optional so the data entry person does not have to
     * provide a value for it.  If 1 is selected, the data entry person must provide a value, or enter a discrepancy
     * note explaining why the field is left blank. This can be used for any RESPONSE_TYPE
     *
     * @param item current item
     * @param required <code>true</code> if current item is required
     */
    static void required(MinimalItem item, boolean required) {
        item.required = required ? 1 : 0
    }

    /**
     * The data type is the format the value should be supplied in.
     * @param item current item
     * @param dataType data type
     */
    static void dataType(GenericItem item, DataType dataType) {
        item.dataType = dataType
    }

    /**
     * Sets the max length for strings.
     * @param item current item
     * @param max maximal length of the string
     */
    static void length(GenericItem item, int max) {
        item.widthDecimal = "$max(d)"
    }

    /**
     * Sets the maximum number of digits of integer.
     * @param item current item
     * @param max maximal number of digits of the integer
     */
    static void digits(GenericItem item, int max) {
        item.widthDecimal = "$max(d)"
    }

    /**
     * Sets the maximum number of digits of real.
     * @param item current item
     * @param max maximal total number of digits
     * @param decimal number of decimal digits
     */
    static void digits(GenericItem item, int max, int decimal) {
        item.widthDecimal = "$max($decimal)"
    }

    /**
     * The data type is the format the value should be supplied in.
     * @param item current item
     * @param dataType data type as class object
     */
    static void dataType(GenericItem item, Class dataType) {
        if (CharSequence.isAssignableFrom(dataType)) {
            item.dataType = DataType.ST
        } else if (Integer.isAssignableFrom(dataType) || int.isAssignableFrom(dataType) || Long.isAssignableFrom(dataType) || long.isAssignableFrom(dataType) || BigInteger.isAssignableFrom(dataType)) {
            item.dataType = DataType.INT
        } else if (Date.isAssignableFrom(dataType) || java.sql.Date.isAssignableFrom(dataType)) {
            item.dataType = DataType.DATE
        } else if (Double.isAssignableFrom(dataType) || double.isAssignableFrom(dataType) || Float.isAssignableFrom(dataType) || float.isAssignableFrom(dataType) || BigDecimal.isAssignableFrom(dataType)) {
            item.dataType = DataType.REAL
        } else {
            throw new IllegalArgumentException("Cannot infer data type from $dataType")
        }
    }

    /**
     * Sets the formula for computation.
     *
     * Should not contain the 'func: ' definition. The formula is wrapped into brackets.
     * @param item current item
     * @param formula formula for computation.
     */
    static void formula(HasCalculation item, String formula) {
        item.calculation = "func: ($formula)"
    }

    private static String normalize(String string) {
        if (!string) {
            return ''
        }
        string.stripIndent().trim()
    }
}
