package org.modelcatalogue.crf.model;

public class ResponseOption {

    private final String text;
    private final String value;

    private Item item;

    public ResponseOption(String text, String value) {
        this(null, value, text);
    }

    public ResponseOption(Item item, String value, String text) {
        this.item = item;
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public Item getItem() {
        return item;
    }

    void setItem(Item item) {
        this.item = item;
    }
}
