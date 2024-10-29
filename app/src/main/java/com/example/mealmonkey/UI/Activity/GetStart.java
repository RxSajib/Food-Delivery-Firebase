package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.databinding.GetstartBinding;

public class GetStart extends AppCompatActivity {

    private GetstartBinding binding;
    private NetworkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.getstart);

        init_view();
    }

    private void init_view(){
        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        binding.LoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AccountHome.class);
            intent.putExtra(DataManager.Data, DataManager.SignIn);
            startActivity(intent);
            finish();
            Animatoo.animateSlideLeft(GetStart.this);
        });

        binding.CreateAccountButton.setOnClickListener(view -> {
            Intent intent = new Intent(GetStart.this, AccountHome.class);
            intent.putExtra(DataManager.Data, DataManager.SignUp);
            startActivity(intent);
            finish();
            Animatoo.animateSlideLeft(GetStart.this);
        });
    }




}