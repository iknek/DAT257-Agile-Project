package com.example.finditv2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The Item class represents each lost item to be found by the user.
 */
public class Item implements Serializable {
    private String description;
    private String category;
    private Date date;
    private String location;

    /**
     * Creates an Item object.
     * @param string The Item objects associated description.
     */
    public Item(String string, String category, Date date, String location){
        this.description = string;
        this.category = category;
        this.date = date;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public String getLocation() {return location;}

    public void setDescription(String description) {
        this.description = description;
    }

    // Om man vill ha date i läsvänligt format: formatter.format(date)
    public Date getDate() {
        return date;
    }
}
