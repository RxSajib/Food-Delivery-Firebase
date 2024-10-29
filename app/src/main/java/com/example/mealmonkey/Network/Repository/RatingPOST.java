package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.UI.Activity.BookMark;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class RatingPOST {

    private Application application;
    private FirebaseAuth Mauth;
    private CollectionReference RetingRef;
    private MutableLiveData<Boolean> data;

    public RatingPOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        RetingRef = FirebaseFirestore.getInstance().collection(DataManager.Rating);
    }

    public LiveData<Boolean> SendRating(String FoodID, String Message, int NoOFRating){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            var timestamp = System.currentTimeMillis();
            var map = new HashMap<String, Object>();
            map.put(DataManager.Comment, Message);
            map.put(DataManager.UID, FirebaseUser.getUid());
            map.put(DataManager.FoodID, FoodID);
            map.put(DataManager.Timestamp, timestamp);
            map.put(DataManager.NoOFRating, NoOFRating);

            RetingRef.document(String.valueOf(timestamp))
                    .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                data.setValue(true);
                            }else {
                                data.setValue(false);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            data.setValue(false);
                        }
                    });
        }
        return data;
    }
}
