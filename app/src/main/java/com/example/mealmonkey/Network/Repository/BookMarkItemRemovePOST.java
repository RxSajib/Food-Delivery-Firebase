package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookMarkItemRemovePOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MBookMarkRef;
    private FirebaseAuth Mauth;

    public BookMarkItemRemovePOST(Application application){
        this.application = application;
        MBookMarkRef = FirebaseFirestore.getInstance().collection(DataManager.BookMark);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> RemoveBookMark(long FoodID){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            MBookMarkRef.document(Muser.getUid())
                    .collection(DataManager.Data)
                    .document(String.valueOf(FoodID)).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                data.setValue(true);
                                Toast.makeText(application, "Remove success", Toast.LENGTH_SHORT).show();
                            }else {
                                data.setValue(false);
                                Toast.makeText(application, "Remove failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    data.setValue(false);
                    Toast.makeText(application, "Remove failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    return data;
    }

}
