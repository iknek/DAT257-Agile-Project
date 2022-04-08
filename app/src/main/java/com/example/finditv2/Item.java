package com.example.finditv2;

import java.io.Serializable;

public class Item implements Serializable {

    String description;
    //hello

    /**
     * Constructs an Item object
     * @param string the description of the lost item.
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
