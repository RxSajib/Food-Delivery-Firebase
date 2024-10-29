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

public class CountryFoodGET {

    private Application application;
    private MutableLiveData<List<FoodData>> data;
    private CollectionReference MFoodRef;

    public CountryFoodGET(Application application){
        this.application = application;
        MFoodRef = FirebaseFirestore.getInstance().collection(DataManager.AllFoodRef);
    }

    public LiveData<List<FoodData>> getCountryFood(String CountryName, int Limit){
        data = new MutableLiveData<>();
        Query FirebaseQuery = MFoodRef.whereEqualTo(DataManager.FoodLocation, CountryName).orderBy(DataManager.Timestamp)
                .limit(Limit);

        FirebaseQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
