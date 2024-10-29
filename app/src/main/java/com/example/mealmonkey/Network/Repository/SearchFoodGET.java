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

public class SearchFoodGET {
    private Application application;
    private MutableLiveData<List<FoodData>> data;
    private CollectionReference MFoodRef;

    public SearchFoodGET(Application application){
        this.application = application;
        MFoodRef = FirebaseFirestore.getInstance().collection(DataManager.AllFoodRef);
    }


    public LiveData<List<FoodData>> SearchFood(String Type, int Limit){
        data = new MutableLiveData<>();
        if(Type.equals(DataManager.All)){
            Query FoodSearchQuery = MFoodRef.orderBy(DataManager.Timestamp, Query.Direction.DESCENDING).limit(Limit);
            FoodSearchQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        }
        else {
            String MyType = Type.toLowerCase();

            Query FoodSearchQuery = MFoodRef.orderBy(DataManager.Search).startAt(MyType).endAt(MyType+"\uf8ff").limit(Limit);
            FoodSearchQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        }
        return data;
    }
}
