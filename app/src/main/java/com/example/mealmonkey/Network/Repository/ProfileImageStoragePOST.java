package com.example.mealmonkey.Network.Repository;

import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anstrontechnologies.corehelper.AnstronCoreHelper;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.ProfileImageDownloadUri;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;


import java.io.File;

public class ProfileImageStoragePOST {

    private Application application;
    private MutableLiveData<ProfileImageDownloadUri> data;
    private StorageReference MStores;
    private AnstronCoreHelper anstronCoreHelper;
    private ProfileImageDownloadUri imageDownloadUri;

    public ProfileImageStoragePOST(Application application) {
        this.application = application;
        data = new MutableLiveData<>();
        MStores = FirebaseStorage.getInstance().getReference();
        anstronCoreHelper = new AnstronCoreHelper(application);
        imageDownloadUri = new ProfileImageDownloadUri();
    }

    public LiveData<ProfileImageDownloadUri> upload_profileimage(Uri posterpath) {

        File file = new File(SiliCompressor.with(application)
                .compress(FileUtils.getPath(application, posterpath), new File(application.getCacheDir(), "temp")));

        Uri fromfile = Uri.fromFile(file);

        MStores.child(DataManager.ProfileImageStores).child(anstronCoreHelper.getFileNameFromUri(fromfile))
                .putFile(fromfile)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if(taskSnapshot.getMetadata() != null){
                            if(taskSnapshot.getMetadata().getReference() != null){
                                Task<Uri> resulturi = taskSnapshot.getStorage().getDownloadUrl();
                                resulturi.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadurl = uri.toString();
                                        imageDownloadUri.setPosterPath(downloadurl);
                                        data.setValue(imageDownloadUri);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        data.setValue(null);
                                        Toast.makeText(application, "Error sending data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        data.setValue(null);
                        Toast.makeText(application, "Error sending data", Toast.LENGTH_SHORT).show();
                    }
                });

        return data;
    }


}
