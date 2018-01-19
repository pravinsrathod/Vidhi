package com.android.sample.listviewwithbaseadapter.model;


/**
 * Item model
 */
public class Item {
    private String itemName;
    private String itemDescription;
    private String itemDetails;

    public Item(String name, String description, String details) {
        this.itemName = name;
        this.itemDescription = description;
        this.itemDetails = details;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public String getItemDetails() { return this.itemDetails;}
}
