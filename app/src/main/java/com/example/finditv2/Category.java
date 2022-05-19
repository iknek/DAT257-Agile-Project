package com.example.finditv2;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;

    /**
     * The Category class represents a group of items categorized by a similar trait
     * @param name
     */
    public Category(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
