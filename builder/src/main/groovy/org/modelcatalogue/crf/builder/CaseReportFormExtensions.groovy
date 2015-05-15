package org.modelcatalogue.crf.builder

import org.modelcatalogue.crf.builder.util.RowDelegate
import org.modelcatalogue.crf.model.Calculation
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.Checkbox
import org.modelcatalogue.crf.model.DataType
import org.modelcatalogue.crf.model.DisplayStatus
import org.modelcatalogue.crf.model.GenericItem
import org.modelcatalogue.crf.model.GridGroup
import org.modelcatalogue.crf.model.Group
import org.modelcatalogue.crf.model.GroupCalculation
import org.modelcatalogue.crf.model.HasDefaultValue
import org.modelcatalogue.crf.model.HasDisplayStatus
import org.modelcatalogue.crf.model.HasPageNumber
import org.modelcatalogue.crf.model.HasResponseLayout
import org.modelcatalogue.crf.model.InstantCalculation
import org.modelcatalogue.crf.model.ItemContainer
import org.modelcatalogue.crf.model.MinimalItem
import org.modelcatalogue.crf.model.MultiSelect
import org.modelcatalogue.crf.model.Radio
import org.modelcatalogue.crf.model.ResponseLayout
import org.modelcatalogue.crf.model.Section
import org.modelcatalogue.crf.model.SingleSelect
import org.modelcatalogue.crf.model.Text
import org.modelcatalogue.crf.model.Textarea
import org.modelcatalogue.crf.model.File as ModelFile

class CaseReportFormExtensions {

    // form extensions

    static void version(CaseReportForm form, String version) {
        form.version = normalize version
    }

    static void versionDescription(CaseReportForm form, String versionDescription) {
        form.versionDescription = normalize versionDescription
    }

    static void revisionNotes(CaseReportForm form, String revisionNotes) {
        form.revisionNotes = normalize revisionNotes
    }

    static Section section(CaseReportForm form, String label, @DelegatesTo(Section) Closure closure) {
        Section section = form.section(label)
        section.with closure
        section
    }


    // section extensions

    static void title(Section section, String title) {
        section.title = normalize title
    }

    static void subtitle(Section section, String subtitle) {
        section.subtitle = normalize subtitle
    }

    static void instructions(Section section, String instructions) {
        section.instructions = normalize instructions
    }

    static Group group(Section section, String label, @DelegatesTo(Group) Closure closure) {
        Group group = section.group(label)
        group.with closure
        group
    }

    static GridGroup grid(Section section, String label, @DelegatesTo(GridGroup) Closure closure) {
        GridGroup group = section.grid(label)
        group.with closure
        group
    }

    // displaying

    static void hide(HasDisplayStatus hasDisplayStatus, boolean hide) {
        hasDisplayStatus.displayStatus = hide ? DisplayStatus.HIDE : DisplayStatus.SHOW
    }

    // having page number extensions

    static void pageNumber(HasPageNumber section, String pageNumber) {
        section.pageNumber = normalize pageNumber
    }


    // checkboxes and radios
    static void layout(HasResponseLayout item, ResponseLayout layout) {
        item.responseLayout = layout
    }

    static void value(HasDefaultValue item, Object value) {
        item.defaultValue = value?.toString()
    }

    // group extensions

    static void header(GridGroup gridGroup, String header) {
        gridGroup.header = normalize header
    }

    static void rows(GridGroup gridGroup, int rows) {
        gridGroup.repeatNum = rows
    }

    static void upto(GridGroup gridGroup, int maxNumberOfRows) {
        gridGroup.repeatMax = maxNumberOfRows
    }


    // item container (group or section) extensions

    /**
     * Finds or creates new text item with predefined type TEXT
     * @param container the parent container
     * @param name the name of the new text input (alphanum, no spaces, separated by underscores, unique)
     * @param closure configuration closure
     * @return new text item or modified one if the item of the same name already exists within container
     */
    static Text text(ItemContainer container, String name, @DelegatesTo(Text) Closure closure) {
        Text item = container.text(name)
        item.with closure
        item
    }

    static Textarea textarea(ItemContainer container, String name, @DelegatesTo(Textarea) Closure closure) {
        Textarea item = container.textarea(name)
        item.with closure
        item
    }


    static SingleSelect singleSelect(ItemContainer container, String name, @DelegatesTo(SingleSelect) Closure closure) {
        SingleSelect item = container.singleSelect(name)
        item.with closure
        item
    }


    static Radio radio(ItemContainer container, String name, @DelegatesTo(Radio) Closure closure) {
        Radio item = container.radio(name)
        item.with closure
        item
    }


    static MultiSelect multiSelect(ItemContainer container, String name, @DelegatesTo(MultiSelect) Closure closure) {
        MultiSelect item = container.multiSelect(name)
        item.with closure
        item
    }


    static Checkbox checkbox(ItemContainer container, String name, @DelegatesTo(Checkbox) Closure closure) {
        Checkbox item = container.checkbox(name)
        item.with closure
        item
    }


    static Calculation calculation(ItemContainer container, String name, @DelegatesTo(Calculation) Closure closure) {
        Calculation item = container.calculation(name)
        item.with closure
        item
    }


    static GroupCalculation groupCalculation(ItemContainer container, String name, @DelegatesTo(GroupCalculation) Closure closure) {
        GroupCalculation item = container.groupCalculation(name)
        item.with closure
        item
    }


    static ModelFile file(ItemContainer container, String name, @DelegatesTo(ModelFile) Closure closure) {
        ModelFile item = container.file(name)
        item.with closure
        item
    }


    static InstantCalculation instantCalculation(ItemContainer container, String name, @DelegatesTo(InstantCalculation) Closure closure) {
        InstantCalculation item = container.instantCalculation(name)
        item.with closure
        item
    }

    /**
     * Places items next to each other.
     *
     *
     * @param container current item container
     * @param closure configuration closure
     * @throws IllegalArgumentException if the current container is GridGroup as columnNumber is not supported
     * for such group.
     */
    static void row(ItemContainer container, @DelegatesTo(RowDelegate) Closure closure) {
        if (container instanceof GridGroup) {
            throw new IllegalArgumentException("Cannot put items into row in repeating group")
        }
        RowDelegate delegate = new RowDelegate(container)
        delegate.with closure
    }

    // item extensions

    static void description(MinimalItem item, String description) {
        item.descriptionLabel = normalize description
    }

    static void question(MinimalItem item, String question) {
        item.leftItemText = normalize question
    }

    static void instructions(MinimalItem item, String instructions) {
        if (item.group instanceof GridGroup) {
            throw new IllegalArgumentException("Instructions (right item text) does not make sense for grid items")
        }
        item.rightItemText = normalize instructions
    }

    static void units(MinimalItem item, String units) {
        item.units = normalize units
    }

    static void header(MinimalItem item, String header) {
        if (item.group instanceof GridGroup) {
            throw new IllegalArgumentException("Header does not make sense for grid items")
        }
        item.header = normalize header
    }

    static void subheader(MinimalItem item, String subheader) {
        if (item.group instanceof GridGroup) {
            throw new IllegalArgumentException("Subheader does not make sense for grid items")
        }
        item.subheader = normalize subheader
    }

    static void questionNumber(MinimalItem item, String questionNumber) {
        item.questionNumber = normalize questionNumber
    }

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

    private static String normalize(String string) {
        if (!string) {
            return ''
        }
        string.stripIndent().trim()
    }
}
