package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookMarkSETGET {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MBookMarkRef;
    private FirebaseAuth Mauth;

    public BookMarkSETGET(Application application){
        this.application = application;
        MBookMarkRef = FirebaseFirestore.getInstance().collection(DataManager.BookMark);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> GetBookMarkExists(long FoodID){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null) {
            MBookMarkRef.document(Muser.getUid())
                    .collection(DataManager.Data)
                    .document(String.valueOf(FoodID))
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                return;
                            }
                            if (value.exists()) {
                                data.setValue(true);
                            } else {
                                data.setValue(false);
                            }
                        }
                    });
        }
        return data;

    }
}
