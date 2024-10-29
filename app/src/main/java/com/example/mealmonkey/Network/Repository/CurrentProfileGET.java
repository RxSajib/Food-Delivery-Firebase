package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class CurrentProfileGET {

    private Application application;
    private MutableLiveData<User> data;
    private CollectionReference MuserRef;
    private FirebaseAuth Mauth;


    public CurrentProfileGET(Application application){
        this.application = application;
        MuserRef = FirebaseFirestore.getInstance().collection(DataManager.Users);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<User> getCurrentUser(){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            MuserRef.document(Muser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        return;
                    }
                    if(value.exists()){
                        data.setValue(value.toObject(User.class));
                    }else {
                        data.setValue(null);
                    }
                }
            });

        }

        return data;
    }
}
