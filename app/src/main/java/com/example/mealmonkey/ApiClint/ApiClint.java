package com.example.mealmonkey.ApiClint;

import com.example.mealmonkey.Data.Model.Data.DataManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClint {

    public static Retrofit retrofit;
    public static Retrofit retrofitemail;

    public Retrofit getCovidApiClint(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(DataManager.Covid19BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public Retrofit getEmailValidationClint(){
        if(retrofitemail == null){
            retrofitemail = new Retrofit.Builder()
                    .baseUrl(DataManager.EmailAddressValidationBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitemail;
    }
}
