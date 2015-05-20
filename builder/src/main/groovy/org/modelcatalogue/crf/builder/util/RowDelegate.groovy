package org.modelcatalogue.crf.builder.util

import org.modelcatalogue.crf.model.Calculation
import org.modelcatalogue.crf.model.Checkbox
import org.modelcatalogue.crf.model.File
import org.modelcatalogue.crf.model.GroupCalculation
import org.modelcatalogue.crf.model.InstantCalculation
import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ItemContainer
import org.modelcatalogue.crf.model.MultiSelect
import org.modelcatalogue.crf.model.Radio
import org.modelcatalogue.crf.model.SingleSelect
import org.modelcatalogue.crf.model.Text
import org.modelcatalogue.crf.model.Textarea

/**
 * Delegate class for placing items next to each other.
 *
 * @see org.modelcatalogue.crf.model.Item#columnNumber
 * @see org.modelcatalogue.crf.builder.CaseReportFormExtensions#row(org.modelcatalogue.crf.model.ItemContainer, groovy.lang.Closure)
 */
class RowDelegate implements ItemContainer {

    private int columnNumber = 1

    private final ItemContainer delegate

    RowDelegate(ItemContainer delegate) {
        this.delegate = delegate
    }

    @Override
    Map<String, Item> getItems() {
        delegate.items
    }

    @Override
    Text text(String name) {
        def item = delegate.text(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    Textarea textarea(String name) {
        def item = delegate.textarea(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    SingleSelect singleSelect(String name) {
        def item = delegate.singleSelect(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    Radio radio(String name) {
        def item = delegate.radio(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    MultiSelect multiSelect(String name) {
        def item = delegate.multiSelect(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    Checkbox checkbox(String name) {
        def item = delegate.checkbox(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    Calculation calculation(String name) {
        def item = delegate.calculation(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    GroupCalculation groupCalculation(String name) {
        def item = delegate.groupCalculation(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    File file(String name) {
        def item = delegate.file(name)
        item.columnNumber = columnNumber++
        item
    }

    @Override
    InstantCalculation instantCalculation(String name) {
        def item = delegate.instantCalculation(name)
        item.columnNumber = columnNumber++
        item
    }
}
