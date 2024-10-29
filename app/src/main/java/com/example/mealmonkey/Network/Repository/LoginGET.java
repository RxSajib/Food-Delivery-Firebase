package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.google.firebase.auth.FirebaseAuth;

public class LoginGET {

    private Application application;
    private FirebaseAuth Mauth;
    private MutableLiveData<NetworkResponse> data;
    private NetworkResponse networkResponse;

    public LoginGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        data = new MutableLiveData<NetworkResponse>();
        networkResponse = new NetworkResponse();
    }

    public LiveData<NetworkResponse> LoginEmailPassword(String Email, String Password){
        if(Email.isEmpty()){
            Toast.makeText(application, "Email require", Toast.LENGTH_LONG).show();
        }
        else if(Password.isEmpty()){
            Toast.makeText(application, "Password require", Toast.LENGTH_LONG).show();
        }
        else {
            Mauth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            networkResponse.setCode(ResponseCode.SUCCESSCODE);
                            data.setValue(networkResponse);
                            Toast.makeText(application, "Login Success", Toast.LENGTH_LONG).show();
                        }else {
                            networkResponse.setCode(ResponseCode.ErrorCODE);
                            data.setValue(networkResponse);
                            Toast.makeText(application, "Error login please check your email and password", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        networkResponse.setCode(ResponseCode.ErrorCODE);
                        data.setValue(networkResponse);
                        Toast.makeText(application, "Error login please check your email and password", Toast.LENGTH_LONG).show();
                    });
        }
        return data;
    }
}
