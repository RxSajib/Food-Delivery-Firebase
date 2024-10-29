package com.example.mealmonkey.Data.Model;

import com.google.gson.annotations.SerializedName;

public class EmailValidation {

    @SerializedName("value")
    private boolean Value;

    @SerializedName("text")
    private String Text;

    public boolean isValue() {
        return Value;
    }

    public void setValue(boolean value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
