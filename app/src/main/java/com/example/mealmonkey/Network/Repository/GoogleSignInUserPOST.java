package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class GoogleSignInUserPOST {

    private Application application;
    private MutableLiveData<NetworkResponse> data;
    private NetworkResponse networkResponse;
    private CollectionReference MuserRef;
    private FirebaseAuth Mauth;

    public GoogleSignInUserPOST(Application application){
        this.application = application;
        networkResponse = new NetworkResponse();
        MuserRef = FirebaseFirestore.getInstance().collection(DataManager.Users);
        Mauth = FirebaseAuth.getInstance();
    }


    public LiveData<NetworkResponse> UploadProfile(String Email, String Name, String Phone, String PhotoUri){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if(task.isSuccessful()){
                        Map<String, Object> map = new HashMap<>();
                        map.put(DataManager.Email, Email);
                        map.put(DataManager.Name, Name);
                        map.put(DataManager.Mobile, Phone);
                        map.put(DataManager.ProfileImageUri, PhotoUri);
                        map.put(DataManager.Token, task.getResult());
                        map.put(DataManager.Birthday, null);
                        map.put(DataManager.Address, null);
                        MuserRef.document(Muser.getUid()).set(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            networkResponse.setCode(DataManager.SUCCESS_CODE);
                                            data.setValue(networkResponse);
                                            Toast.makeText(application, "Login success ", Toast.LENGTH_SHORT).show();
                                        }else {
                                            networkResponse.setCode(DataManager.ERROR_CODE);
                                            data.setValue(networkResponse);
                                            Toast.makeText(application, "Error login "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else {
                        networkResponse.setCode(DataManager.ERROR_CODE);
                        data.setValue(networkResponse);
                        Toast.makeText(application, "Error login "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    networkResponse.setCode(DataManager.ERROR_CODE);
                    data.setValue(networkResponse);
                    Toast.makeText(application, "Error login "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return data;
    }
}
