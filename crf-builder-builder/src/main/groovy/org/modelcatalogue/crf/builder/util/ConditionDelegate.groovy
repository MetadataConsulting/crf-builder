package org.modelcatalogue.crf.builder.util

/**
 * Delegate container for configuring simple condition display.
 *
 * Example usage is <code>when 'exam' is 2 otherwise 'Comments should be empty if exam is normal.'</code>
 *
 * @see org.modelcatalogue.crf.model.Item#simpleConditionalDisplay
 */
class ConditionDelegate {

    String item
    String value
    String stale = "This item should no longer be visible. Please, remove the current value."

    /**
     * @param name name of the item which triggers displaying of current item
     * @return self
     */
    ConditionDelegate when(String name) {
        this.item = name
        this
    }

    /**
     * @param value value of response option which triggers displaying of current item
     * @return self
     */
    ConditionDelegate is(Object value) {
        this.value = value?.toString()
        this
    }

    /**
     * @param textIfValueEnteredButShouldBeHidden warning text which appears if current item has value but should be hidden
     * @return self
     */
    ConditionDelegate otherwise(String textIfValueEnteredButShouldBeHidden) {
        this.stale = textIfValueEnteredButShouldBeHidden
        this
    }

}
