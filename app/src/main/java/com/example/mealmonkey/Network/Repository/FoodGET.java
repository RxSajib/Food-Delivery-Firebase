package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FoodGET {

    private Application application;
    private MutableLiveData<FoodData> data;
    private CollectionReference MAllFoodRef;

    public FoodGET(Application application){
        this.application = application;
        MAllFoodRef = FirebaseFirestore.getInstance().collection(DataManager.AllFoodRef);
    }

    public LiveData<FoodData> GetFood(String FoodID){
        data = new MutableLiveData<>();
        MAllFoodRef.document(FoodID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            return;
                        }
                        if(value.exists()){
                            data.setValue(value.toObject(FoodData.class));
                        }else {
                            data.setValue(null);
                        }
                    }
                });
        return data;
    }
}
