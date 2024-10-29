package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.R;
import com.example.mealmonkey.databinding.AccounthomeBinding;

public class AccountHome extends AppCompatActivity {

    private AccounthomeBinding binding;
    private String Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.accounthome);
        Data = getIntent().getStringExtra(DataManager.Data);


        String VideoPath = new StringBuilder("android.resource://")
                .append(getPackageName())
                .append("/raw/introvideo")
                .toString();
        binding.ViewView.setVideoPath(VideoPath);
        binding.ViewView.start();
        binding.ViewView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);

                float VideoRatio = mp.getVideoHeight() / (float) mp.getVideoHeight();
                float ScreenRatio = binding.ViewView.getWidth() / (float) binding.ViewView.getHeight();

                float Scal = VideoRatio / ScreenRatio;

                if(Scal >= 1f){
                    binding.ViewView.setScaleX(Scal);
                }else {
                    binding.ViewView.setScaleX(1f / Scal);
                }
            }
        });



        if(Data.equals(DataManager.SignIn)){
            setlogin(new SignIn());
        }
        if(Data.equals(DataManager.SignUp)){
            setlogin(new SignUp());
        }

    }

    private void setlogin(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment);
        transaction.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.ViewView.start();
    }
}