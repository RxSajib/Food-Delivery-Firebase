package com.example.mealmonkey;

public class RatingModel {

    private String Comment, FoodID, UID;
    private int NoOFRating;
    private long Timestamp;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getNoOFRating() {
        return NoOFRating;
    }

    public void setNoOFRating(int noOFRating) {
        NoOFRating = noOFRating;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }
}
