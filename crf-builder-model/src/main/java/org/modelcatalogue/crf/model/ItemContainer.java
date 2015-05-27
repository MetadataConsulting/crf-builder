package org.modelcatalogue.crf.model;

import java.util.Map;

public interface ItemContainer {

    /**
     * Returns map of all items registered within current container having item's name as a map key.
     * @return map of all items registered within current container having item's name as a map key
     */
    Map<String, Item> getItems();

    Text text(String name);

    Textarea textarea(String name);

    SingleSelect singleSelect(String name);

    Radio radio(String name);

    MultiSelect multiSelect(String name);

    Checkbox checkbox(String name);

    Calculation calculation(String name);

    GroupCalculation groupCalculation(String name);

    File file(String name);

    InstantCalculation instantCalculation(String name);

}
