package com.example.finditv2;

import java.io.Serializable;

public class Item implements Serializable {

    String description;
    /**
     * Item constructor.
     * @param string = Item description
     */
    public Item(String string){
        this.description = string;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
