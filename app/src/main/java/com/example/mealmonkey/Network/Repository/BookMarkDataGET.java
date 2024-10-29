package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class BookMarkDataGET {

    private Application application;
    private MutableLiveData<List<FoodData>> data;
    private CollectionReference MBookMarkRef;
    private FirebaseAuth Mauth;

    public BookMarkDataGET(Application application){
        this.application = application;
        MBookMarkRef = FirebaseFirestore.getInstance().collection(DataManager.BookMark);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<List<FoodData>> GETBookMarkData(){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            MBookMarkRef.document(Muser.getUid())
                    .collection(DataManager.Data)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
