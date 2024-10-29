package com.example.mealmonkey.Data.Model;

public class FoodSize {

    private String SizeTitle;
    private String Price;

    public FoodSize(String sizeTitle, String price) {
        SizeTitle = sizeTitle;
        Price = price;
    }

    public String getSizeTitle() {
        return SizeTitle;
    }

    public void setSizeTitle(String sizeTitle) {
        SizeTitle = sizeTitle;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
