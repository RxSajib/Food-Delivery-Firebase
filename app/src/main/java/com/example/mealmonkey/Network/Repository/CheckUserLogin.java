package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Response.ResponseCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CheckUserLogin {

    private Application application;
    private FirebaseAuth Mauth;
    private MutableLiveData<Integer> data;


    public CheckUserLogin(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        data = new MutableLiveData<>();
    }

    public LiveData<Integer> CheckUserLoginAccount(){
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            data.setValue(ResponseCode.SUCCESSCODE);
        }
        else {
            data.setValue(ResponseCode.ErrorCODE);
        }
        return data;
    }
}
