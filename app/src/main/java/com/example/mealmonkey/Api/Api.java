package com.example.mealmonkey.Api;

import com.example.mealmonkey.Data.Model.CovidCountry;
import com.example.mealmonkey.Data.Model.EmailValidationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    @GET("countries/bangladesh")
    Call<CovidCountry> GetBangladeshCovidData();


    @GET("v1")
    Call<EmailValidationResponse> EmailValidationResponse(
           @Query("api_key") String Key,
           @Query("email") String EmailAddress
    );

}
