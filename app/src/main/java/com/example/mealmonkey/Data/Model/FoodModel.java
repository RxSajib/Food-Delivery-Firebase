package com.example.mealmonkey.Data.Model;

import java.io.Serializable;

public class FoodModel {

    private String FoodName, FoodLocation, SellingTime;
    private String FoodImage;
    private float Rating;
    private int FoodNumOFSelling;
    private String FoodMadeBy;
    private String Restaurant;
    private int Price;


    public FoodModel(String foodName, String foodLocation, String sellingTime, String foodImage, float rating, int foodNumOFSelling, String foodMadeBy) {
        FoodName = foodName;
        FoodLocation = foodLocation;
        SellingTime = sellingTime;
        FoodImage = foodImage;
        Rating = rating;
        FoodNumOFSelling = foodNumOFSelling;
        FoodMadeBy = foodMadeBy;
    }

    public FoodModel(String foodName, String foodLocation, String sellingTime, String foodImage, float rating, int foodNumOFSelling, String foodMadeBy, String restaurant) {
        FoodName = foodName;
        FoodLocation = foodLocation;
        SellingTime = sellingTime;
        FoodImage = foodImage;
        Rating = rating;
        FoodNumOFSelling = foodNumOFSelling;
        FoodMadeBy = foodMadeBy;
        Restaurant = restaurant;
    }

    public FoodModel(String foodName, String foodLocation, String sellingTime, String foodImage, float rating, int foodNumOFSelling, String foodMadeBy, String restaurant, int price) {
        FoodName = foodName;
        FoodLocation = foodLocation;
        SellingTime = sellingTime;
        FoodImage = foodImage;
        Rating = rating;
        FoodNumOFSelling = foodNumOFSelling;
        FoodMadeBy = foodMadeBy;
        Restaurant = restaurant;
        Price = price;
    }

    public FoodModel(String foodName, String foodLocation, String sellingTime, String foodImage, float rating, int foodNumOFSelling, String foodMadeBy, int price) {
        FoodName = foodName;
        FoodLocation = foodLocation;
        SellingTime = sellingTime;
        FoodImage = foodImage;
        Rating = rating;
        FoodNumOFSelling = foodNumOFSelling;
        FoodMadeBy = foodMadeBy;
        Price = price;
    }



    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodLocation() {
        return FoodLocation;
    }

    public void setFoodLocation(String foodLocation) {
        FoodLocation = foodLocation;
    }

    public String getSellingTime() {
        return SellingTime;
    }

    public void setSellingTime(String sellingTime) {
        SellingTime = sellingTime;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public int getFoodNumOFSelling() {
        return FoodNumOFSelling;
    }

    public void setFoodNumOFSelling(int foodNumOFSelling) {
        FoodNumOFSelling = foodNumOFSelling;
    }

    public String getFoodMadeBy() {
        return FoodMadeBy;
    }

    public void setFoodMadeBy(String foodMadeBy) {
        FoodMadeBy = foodMadeBy;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }


    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
