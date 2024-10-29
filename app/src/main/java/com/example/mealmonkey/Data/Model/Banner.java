package com.example.mealmonkey.Data.Model;

public class Banner {
    private String FoodImage;

    public Banner(){

    }

    public Banner(String foodImage) {
        FoodImage = foodImage;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }
}
