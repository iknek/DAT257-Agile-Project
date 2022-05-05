package com.example.finditv2;


import android.net.Uri;

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
    private String imageUri;

    /**
     * Creates an Item object.
     * @param string The Item objects associated description.
     */
    public Item(String string, String category, Date date, String location){
        this.description = string;
        this.category = category;
        this.date = date;
        this.location = location;
        this.imageUri = null;
    }

    /**
     * Constructor used when there is a picture of the item
     * @param string
     * @param category
     * @param date
     * @param location
     * @param imageUri
     */


    public Item(String string, String category, Date date, String location, String imageUri){
        this.description = string;
        this.category = category;
        this.date = date;
        this.location = location;
        this.imageUri = imageUri;
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

    public String getImageUri() {
        return imageUri;
    }

    public boolean equals(Item obj){
        boolean description = obj.getDescription().equals(this.description);
        boolean location = obj.getLocation().equals(this.location);
        boolean category = obj.getCategory().equals(this.category);
        boolean date = obj.getDate().equals(this.date);
        return description&&location&&category&&date;
    }
}
