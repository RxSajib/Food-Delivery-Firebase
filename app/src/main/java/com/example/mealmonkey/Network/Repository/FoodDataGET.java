package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FoodDataGET {

    private Application application;
    private CollectionReference MAllFoodRef;
    private MutableLiveData<List<FoodData>> data;

    public FoodDataGET(Application application){
        this.application = application;
        MAllFoodRef = FirebaseFirestore.getInstance().collection(DataManager.AllFoodRef);
    }

    public LiveData<List<FoodData>> GetFoodData(String FoodType, int Limit){
        data = new MutableLiveData<>();
        Query FoodDataQuery = MAllFoodRef.whereEqualTo(DataManager.FoodType, FoodType).limit(Limit).orderBy(DataManager.Timestamp, Query.Direction.DESCENDING);
        FoodDataQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(!value.isEmpty()){
                    for(DocumentChange ds : value.getDocumentChanges()){
                        if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                            data.setValue(value.toObjects(FoodData.class));
                        }
                    }
                }else {
                    data.setValue(null);
                }
            }
        });
        return data;
    }
}
