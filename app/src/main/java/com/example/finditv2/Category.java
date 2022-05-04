package com.example.finditv2;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    private String name;

    /**
     * The Category class represents a group of items categorized by a similar trait
     * @param name
     */
    public Category(String name) {
        this.name = name;
    }

    public void setCategory(String cat){

    }

    public String getName(){
        return name;
    }
}
