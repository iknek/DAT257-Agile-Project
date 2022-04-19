package com.example.finditv2;

import java.io.Serializable;

/**
 * The Item class represents each lost item to be found by the user.
 */
public class Item implements Serializable {

    String description;

    Category category;

    /**
     * Creates an Item object.
     * @param string The Item objects associated description.
     */
    public Item(String string, Category category){
        this.description = string;
        this.category = category;
    }

    public Item(String string){
        this.description = string;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
