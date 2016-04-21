package org.modelcatalogue.crf.model;

import javax.validation.Valid;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

abstract class AbstractItemsContainer implements ItemContainer {

    AbstractItemsContainer() {}

    @Valid protected Map<String, Item> items = new LinkedHashMap<String, Item>();

    private Item item(ResponseType type, String name) {
        if (items.containsKey(name)) {
            return items.get(name);
        }
        Item item = new Item(type);
        item.setName(name);
        addItem(item);
        return item;
    }

    protected abstract Item addItem(Item item);

    public final Map<String, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    @Override
    public Text text(String name) {
        return item(ResponseType.TEXT, name);
    }

    @Override
    public Textarea textarea(String name) {
        return item(ResponseType.TEXTAREA, name);
    }

    @Override
    public SingleSelect singleSelect(String name) {
        return item(ResponseType.SINGLE_SELECT, name);
    }

    @Override
    public Radio radio(String name) {
        return item(ResponseType.RADIO, name);
    }

    @Override
    public MultiSelect multiSelect(String name) {
        return item(ResponseType.MULTI_SELECT, name);
    }

    @Override
    public Checkbox checkbox(String name) {
        return item(ResponseType.CHECKBOX, name);
    }

    @Override
    public Calculation calculation(String name) {
        return item(ResponseType.CALCULATION, name);
    }

    @Override
    public GroupCalculation groupCalculation(String name) {
        return item(ResponseType.GROUP_CALCULATION, name);
    }

    @Override
    public File file(String name) {
        Item item = item(ResponseType.FILE, name);
        item.setDataType(DataType.FILE);
        return item;
    }

    @Override
    public InstantCalculation instantCalculation(String name) {
        return item(ResponseType.INSTANT_CALCULATION, name);
    }
}
