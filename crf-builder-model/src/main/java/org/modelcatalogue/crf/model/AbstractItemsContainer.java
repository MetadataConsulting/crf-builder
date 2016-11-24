package org.modelcatalogue.crf.model;

import javax.validation.Valid;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void sortItemsByQuestionNumber() {
        SortedMap<Long, Item> itemsByIndex = new TreeMap<Long, Item>();
        Set<String> questionNumbers = new HashSet<String>(items.size());
        int offset = 0;

        long lastIndex = 0;
        for (Item item : items.values()) {
            String questionNumber = item.getQuestionNumber();
            if (questionNumber != null) {
                if (questionNumbers.contains(questionNumber)) {
                    offset++;
                }
                questionNumbers.add(questionNumber);
                long index = toQuestionNumberIndex(offset, questionNumber);
                lastIndex = index;
                itemsByIndex.put(index, item);
            } else {
                itemsByIndex.put(++lastIndex, item);
            }
        }

        Map<String, Item> result = new LinkedHashMap<String, Item>(items.size());
        for (Item item : itemsByIndex.values()) {
            result.put(item.getName(), item);
        }
        this.items = result;
    }

    private static Pattern SECOND_LEVEL_QUESTION = Pattern.compile("(\\d+)\\.(\\d+)");

    /**
     * If the string is just number itreturns the number times 1000.
     * If the string contains two numbers separated by dot it returns the first number times 1000 plus the second number.
     */
    private static long toQuestionNumberIndex(int offset, String string) {
        if (string.matches("\\d+")) {
            return offset * 1000000000 + Integer.parseInt(string, 10) * 1000000;
        }

        Matcher matcher = SECOND_LEVEL_QUESTION.matcher(string);
        if (matcher.matches()) {
            return offset * 1000000000 + Integer.parseInt(matcher.group(1), 10) * 1000000 + Integer.parseInt(matcher.group(2), 10) * 1000;
        }

        return offset * 1000000000;
    }
}
