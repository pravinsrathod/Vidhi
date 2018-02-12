package com.android.sample.listviewwithbaseadapter.model;

import java.io.Serializable;

/**
 * Created by KIT912 on 2/11/2018.
 */

public class ListPojo implements Serializable {
    public String ItemName="";

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int qty=0;
}
