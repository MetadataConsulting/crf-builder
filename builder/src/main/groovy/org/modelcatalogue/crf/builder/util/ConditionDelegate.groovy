package org.modelcatalogue.crf.builder.util

class ConditionDelegate {

    String item
    String value
    String stale = "This item should no longer be visible. Please, remove the current value."

    ConditionDelegate when(String name) {
        this.item = name
        this
    }

    ConditionDelegate is(Object value) {
        this.value = value
        this
    }

    ConditionDelegate otherwise(String textIfValueEnteredButShouldBeHidden) {
        this.stale = textIfValueEnteredButShouldBeHidden
        this
    }

}
