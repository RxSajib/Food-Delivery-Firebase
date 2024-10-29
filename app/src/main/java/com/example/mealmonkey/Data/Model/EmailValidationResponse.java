package com.example.mealmonkey.Data.Model;

import com.google.gson.annotations.SerializedName;

public class EmailValidationResponse {


    @SerializedName("is_valid_format")
    private EmailValidation emailValidation;

    public EmailValidation getEmailValidation() {
        return emailValidation;
    }

    public void setEmailValidation(EmailValidation emailValidation) {
        this.emailValidation = emailValidation;
    }
}
