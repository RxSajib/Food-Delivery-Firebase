package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserPOST {

    private Application application;
    private MutableLiveData<NetworkResponse> data;
    private CollectionReference MuserRef;
    private FirebaseAuth Mauth;
    private NetworkResponse networkResponse;

    public UserPOST(Application application){
        this.application = application;
        MuserRef = FirebaseFirestore.getInstance().collection(DataManager.Users);
        Mauth = FirebaseAuth.getInstance();
        networkResponse = new NetworkResponse();
    }

    public LiveData<NetworkResponse> SendUserData(String Name, String Email, String Address, String Phone, String Birthday){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){

            Map<String, Object> map = new HashMap<>();
            map.put(DataManager.Name, Name);
            map.put(DataManager.Email, Email);
            map.put(DataManager.Address, Address);
            map.put(DataManager.Mobile, Phone);
            map.put(DataManager.Birthday, Birthday);

            MuserRef.document(Muser.getUid()).update(map)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            networkResponse.setCode(ResponseCode.SUCCESSCODE);
                            data.setValue(networkResponse);
                            Toast.makeText(application, "Update success", Toast.LENGTH_SHORT).show();
                        }else {
                            networkResponse.setCode(ResponseCode.ErrorCODE);
                            data.setValue(networkResponse);
                            Toast.makeText(application, "Error send data "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        networkResponse.setCode(ResponseCode.ErrorCODE);
                        data.setValue(networkResponse);
                        Toast.makeText(application, "Error send data "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
        return data;
    }
}
