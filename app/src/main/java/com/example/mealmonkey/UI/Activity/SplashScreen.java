package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;

public class SplashScreen extends AppCompatActivity {

    private NetworkViewModel responseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler(Looper.myLooper())
                .postDelayed(
                        () -> gotohomepage(),
                        3000);


    }

    private void gotohomepage(){
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        Animatoo.animateSlideLeft(SplashScreen.this);

    }
}