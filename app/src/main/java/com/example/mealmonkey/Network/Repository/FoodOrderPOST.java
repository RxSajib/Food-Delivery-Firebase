package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.UI.Activity.BookMark;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FoodOrderPOST {

    private Application application;
    private FirebaseAuth Mauth;
    private CollectionReference OrderRef;
    private MutableLiveData<Boolean> data;

    public FoodOrderPOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        OrderRef = FirebaseFirestore.getInstance().collection("Order");
    }

    public LiveData<Boolean> OrderFood(String FirstName, String LastName, String PhoneNumber, String StreetAddress, String City, String Area, String Note, List<CartModel> list){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){

            var Timestamp = System.currentTimeMillis();

            var map = new HashMap<String, Object>();
            map.put("FirstName", FirstName);
            map.put("LastName", LastName);
            map.put("PhoneNumber", PhoneNumber);
            map.put("StreetAddress", StreetAddress);
            map.put("City", City);
            map.put("Area", Area);
            map.put("Note", Note);
            map.put("Items", list);
            map.put("ID", Timestamp);
            map.put("Time", Timestamp);
            map.put("UID", FirebaseUser.getUid());

            OrderRef.document(String.valueOf(Timestamp))
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
