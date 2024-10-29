package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PromotionsItemSizeGET {

    private Application application;
    private MutableLiveData<Integer> data;
    private CollectionReference MAllFoodRef;

    public PromotionsItemSizeGET(Application application){
        this.application = application;
        MAllFoodRef = FirebaseFirestore.getInstance().collection(DataManager.AllFoodRef);
    }

    public LiveData<Integer> getPromotionsItemSize(String FoodItem){
        data = new MutableLiveData<>();
        Query Promotions = MAllFoodRef.whereEqualTo(DataManager.FoodType, FoodItem);
        Promotions.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(!value.isEmpty()){
                    for(DocumentChange ds : value.getDocumentChanges()){
                        if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                            data.setValue(value.size());
                        }
                    }
                }else {
                    data.setValue(0);
                }
            }
        });
        return data;
    }
}
