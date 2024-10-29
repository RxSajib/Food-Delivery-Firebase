package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Api.Api;
import com.example.mealmonkey.ApiClint.ApiClint;
import com.example.mealmonkey.Data.Model.EmailValidationResponse;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailValidationGET {

    private Application application;
    private MutableLiveData<EmailValidationResponse> data;
    private Api MApi;

    public EmailValidationGET(Application application){
        this.application = application;
        MApi = new ApiClint().getEmailValidationClint().create(Api.class);
    }

    public LiveData<EmailValidationResponse> getEmailValidation(String ApiKey, String EmailAddress){
        data = new MutableLiveData<>();

        MApi.EmailValidationResponse(ApiKey, EmailAddress)
                .enqueue(new Callback<EmailValidationResponse>() {
                    @Override
                    public void onResponse(@Nullable Call<EmailValidationResponse> call,@Nullable Response<EmailValidationResponse> response) {
                        if(response.isSuccessful()){
                            data.setValue(response.body());
                        }else {
                            data.setValue(null);
                            Toast.makeText(application, "Error server problem "+String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@Nullable Call<EmailValidationResponse> call,@Nullable Throwable t) {
                        data.setValue(null);
                        Toast.makeText(application, "Error server problem"+String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                });

        return data;
    }
}
