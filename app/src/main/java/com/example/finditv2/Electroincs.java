package com.example.finditv2;

public class Electroincs extends Category {

    private String manfacturer;

    public Electroincs(String manfacturer) {
        super(Electroincs.class.getName());
        this.manfacturer = manfacturer;
    }

    public Electroincs() {
        super(Electroincs.class.getName());
    }

    public String getManfacturer() {
        return manfacturer;
    }

    public void setManfacturer(String manfacturer) {
        this.manfacturer = manfacturer;
    }
}
