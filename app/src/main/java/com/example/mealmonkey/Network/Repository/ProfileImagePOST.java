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

import java.util.HashMap;
import java.util.Map;

public class ProfileImagePOST extends Application {

    private Application application;
    private MutableLiveData<NetworkResponse> data;
    private CollectionReference MuserRef;
    private FirebaseAuth Mauth;
    private NetworkResponse networkResponse;

    public ProfileImagePOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MuserRef  = FirebaseFirestore.getInstance().collection(DataManager.Users);
        networkResponse = new NetworkResponse();
    }

    public LiveData<NetworkResponse> ProfileImagePOST(String ImageUri){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            Map<String, Object> usermap = new HashMap<>();
            usermap.put(DataManager.ProfileImageUri, ImageUri);
            MuserRef.document(Muser.getUid())
                    .update(usermap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                networkResponse.setCode(DataManager.SUCCESS_CODE);
                                data.setValue(networkResponse);
                            }else {
                                networkResponse.setCode(DataManager.ERROR_CODE);
                                data.setValue(networkResponse);
                                Toast.makeText(application, "Error update image "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    networkResponse.setCode(DataManager.ERROR_CODE);
                    data.setValue(networkResponse);
                    Toast.makeText(application, "Error update image "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return data;
    }
}
