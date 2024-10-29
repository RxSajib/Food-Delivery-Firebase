package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class SignUpPOST {

    private Application application;
    private MutableLiveData<NetworkResponse> data;
    private FirebaseAuth Mauth;
    private NetworkResponse networkResponse;
    private FirebaseFirestore MuserRef;


    public SignUpPOST(Application application) {
        this.application = application;
        data = new MutableLiveData<>();
        Mauth = FirebaseAuth.getInstance();
        networkResponse = new NetworkResponse();
        MuserRef = FirebaseFirestore.getInstance();
    }

    public LiveData<NetworkResponse> SignUpAccount(String Name, String Email, String Mobile, String Address, String Password, String ConfirmPassword) {
        Mauth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseMessaging.getInstance().getToken()
                                .addOnSuccessListener(s -> {
                                    long Timestamp = System.currentTimeMillis();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put(DataManager.Name, Name);
                                    map.put(DataManager.Email, Email);
                                    map.put(DataManager.Mobile, Mobile);
                                    map.put(DataManager.Address, Address);
                                    map.put(DataManager.Birthday, null);
                                    map.put(DataManager.Timestamp, Timestamp);
                                    map.put(DataManager.UID, Mauth.getCurrentUser().getUid());
                                    map.put(DataManager.Token, s);

                                    MuserRef.collection(DataManager.Users).document(Mauth.getCurrentUser().getUid())
                                            .set(map).addOnCompleteListener(task1 -> {
                                                if(task1.isSuccessful()){
                                                    networkResponse.setCode(ResponseCode.SUCCESSCODE);
                                                    data.setValue(networkResponse);
                                                }else {
                                                    networkResponse.setCode(ResponseCode.ErrorCODE);
                                                    data.setValue(networkResponse);
                                                    Toast.makeText(application, "Error SignUp Account", Toast.LENGTH_LONG).show();
                                                }
                                            }).addOnFailureListener(e -> {
                                        networkResponse.setCode(ResponseCode.ErrorCODE);
                                        data.setValue(networkResponse);
                                        Toast.makeText(application, "Error SignUp Account", Toast.LENGTH_LONG).show();
                                    });

                                });


                    } else {
                        networkResponse.setCode(ResponseCode.ErrorCODE);
                        data.setValue(networkResponse);
                        Toast.makeText(application, "Error SignUp Account", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
            networkResponse.setCode(ResponseCode.ErrorCODE);
            data.setValue(networkResponse);
            Toast.makeText(application, "Error SignUp Account", Toast.LENGTH_SHORT).show();
        });
        return data;
    }
}
