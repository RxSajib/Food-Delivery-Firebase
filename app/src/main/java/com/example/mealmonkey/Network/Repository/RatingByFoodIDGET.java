package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.RatingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class RatingByFoodIDGET {

    private Application application;
    private MutableLiveData<List<RatingModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference RetingRef;

    public RatingByFoodIDGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        RetingRef = FirebaseFirestore.getInstance().collection(DataManager.Rating);
    }

    public LiveData<List<RatingModel>> GetRatingByFoodID(String FoodID, int Limit){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            var q = RetingRef.whereEqualTo(DataManager.FoodID, FoodID).limit(Limit).orderBy(DataManager.Timestamp, Query.Direction.DESCENDING);
            q.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        data.setValue(null);
                        return;
                    }
                    if(!value.isEmpty()){
                        data.setValue(value.toObjects(RatingModel.class));
                    }else {
                        data.setValue(null);
                    }
                }
            });
        }
        return data;
    }
}
