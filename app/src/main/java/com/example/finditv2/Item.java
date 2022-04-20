package com.example.finditv2;

import java.io.Serializable;
import java.util.List;

/**
 * The Item class represents each lost item to be found by the user.
 */
public class Item implements Serializable {
    String description;
    String category;

    /**
     * Creates an Item object.
     * @param string The Item objects associated description.
     */
    public Item(String string, String category){
        this.description = string;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
