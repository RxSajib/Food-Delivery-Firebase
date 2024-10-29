package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PromotionSizeGET {

    private Application application;
    private MutableLiveData<Integer> data;
    private CollectionReference MAllFoodRef;

    public PromotionSizeGET(Application application){
        this.application = application;
        MAllFoodRef = FirebaseFirestore.getInstance().collection(DataManager.AllFoodRef);
    }

    public LiveData<Integer> PromotionSize(String Data){
        data = new MutableLiveData<>();
        Query FoodQuery = MAllFoodRef.whereEqualTo(DataManager.Promotions,Data);
        FoodQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(!value.isEmpty()){
                    data.setValue(value.size());
                }else {
                    data.setValue(0);
                }
            }
        });
        return data;
    }
}
