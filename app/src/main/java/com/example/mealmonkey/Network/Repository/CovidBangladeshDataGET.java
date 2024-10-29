package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Api.Api;
import com.example.mealmonkey.ApiClint.ApiClint;
import com.example.mealmonkey.Data.Model.CovidCountry;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidBangladeshDataGET {

    private Application application;
    private MutableLiveData<CovidCountry> data;
    private Api MApi;

    public CovidBangladeshDataGET(Application application){
        this.application = application;
        MApi = new ApiClint().getCovidApiClint().create(Api.class);
    }

    public LiveData<CovidCountry> GetBangladeshCovidData(){
        data = new MutableLiveData<>();

        MApi.GetBangladeshCovidData().enqueue(new Callback<CovidCountry>() {
            @Override
            public void onResponse(@Nullable Call<CovidCountry> call, @Nullable Response<CovidCountry> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    data.setValue(null);
                    Toast.makeText(application, "Error get covid data ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@Nullable Call<CovidCountry> call,@Nullable Throwable t) {
                data.setValue(null);
                Toast.makeText(application, "Error get covid data", Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }
}
