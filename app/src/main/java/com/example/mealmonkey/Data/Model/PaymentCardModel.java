package com.example.mealmonkey.Data.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PaymentDB")
public class PaymentCardModel {

    @PrimaryKey()
    @NonNull
    private String CardNumber;
    private String Month;
    private String Year;
    private String SequrityCode;
    private String FirstName;
    private String LastName;



    public PaymentCardModel(){

    }

    public PaymentCardModel( String cardNumber, String month, String year, String sequrityCode, String firstName, String lastName) {
        CardNumber = cardNumber;
        Month = month;
        Year = year;
        SequrityCode = sequrityCode;
        FirstName = firstName;
        LastName = lastName;
    }


    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getSequrityCode() {
        return SequrityCode;
    }

    public void setSequrityCode(String sequrityCode) {
        SequrityCode = sequrityCode;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
