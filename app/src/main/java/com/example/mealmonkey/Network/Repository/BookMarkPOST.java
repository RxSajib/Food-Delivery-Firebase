package com.example.mealmonkey.Network.Repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookMarkPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MBookMarkRef;
    private FirebaseAuth Mauth;

    public BookMarkPOST(Application application){
        this.application = application;
        MBookMarkRef = FirebaseFirestore.getInstance().collection(DataManager.BookMark);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> SetBookMark(long FoodID, FoodData foodData){
        data = new MutableLiveData<>();

        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            Map<String, Object> map = new HashMap<>();
            map.put(DataManager.FoodTitle, foodData.getFoodTitle());
            map.put(DataManager.FoodMadeBy, foodData.getFoodMadeBy());
            map.put(DataManager.FoodDetails, foodData.getFoodDetails());
            map.put(DataManager.FoodID, foodData.getFoodID());
            map.put(DataManager.FoodLocation, foodData.getFoodLocation());
            map.put(DataManager.FoodNoOFSelling, foodData.getFoodNoOFSelling());
            map.put(DataManager.FoodOffer, foodData.getFoodOffer());
            map.put(DataManager.FoodPrice, foodData.getFoodPrice());
            map.put(DataManager.FoodType, foodData.getFoodType());
            map.put(DataManager.FoodImageOne, foodData.getImageOne());
            map.put(DataManager.FoodImageTwo, foodData.getImageTwo());
            map.put(DataManager.FoodImageThree, foodData.getImageThree());
            map.put(DataManager.FoodImageFour, foodData.getImageFour());
            map.put(DataManager.Promotions, foodData.getPromotions());
            map.put(DataManager.Rating, foodData.getRating());
            map.put(DataManager.RestaurantsLocation, foodData.getRestaurantsLocation());

            MBookMarkRef.document(Muser.getUid())
                   .collection(DataManager.Data).document(String.valueOf(FoodID))
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(!document.exists()){
                            MBookMarkRef.document(Muser.getUid()).collection(DataManager.Data)
                                    .document(String.valueOf(FoodID)).set(map);
                            data.setValue(true);
                        }else {
                            MBookMarkRef.document(Muser.getUid()).collection(DataManager.Data)
                                    .document(String.valueOf(FoodID)).delete();
                            data.setValue(false);
                        }
                    }
                }
            });
        }

        return data;
    }
}
