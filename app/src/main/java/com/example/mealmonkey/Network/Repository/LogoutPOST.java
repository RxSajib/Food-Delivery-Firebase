package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Response.ResponseCode;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutPOST {

    private Application application;
    private MutableLiveData<Integer> data;
    private FirebaseAuth Mauth;

    public LogoutPOST(Application application){
        this.application = application;
        data = new MutableLiveData<>();
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Integer> LogOutAccount(){
        Mauth.signOut();
        data.setValue(ResponseCode.SUCCESSCODE);

        return data;
    }
}
