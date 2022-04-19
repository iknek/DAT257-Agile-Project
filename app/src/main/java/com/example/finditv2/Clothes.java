package com.example.finditv2;

public class Clothes extends Category{
    private String brand;
    public Clothes (String brand){
        super(Clothes.class.getName());
    }
    public Clothes(){
        super(Clothes.class.getName());
        this.brand = "Unknown";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
