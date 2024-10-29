package com.example.mealmonkey.UI.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.L;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.CountryFoodAdapter;
import com.example.mealmonkey.databinding.CountryfoodBinding;

import java.util.List;

public class CountryFood extends AppCompatActivity {

    private CountryfoodBinding binding;
    private String Country;
    private NetworkViewModel networkViewModel;
    private CountryFoodAdapter countryFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.countryfood);
        Country = getIntent().getStringExtra(DataManager.Data);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        init_view();
        ServerData();
    }

    private void ServerData() {
        countryFoodAdapter = new CountryFoodAdapter();
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.RecyclerView.setAdapter(countryFoodAdapter);
        networkViewModel.GetCountryFood(Country, 20)
                .observe(this, new Observer<List<FoodData>>() {
                    @Override
                    public void onChanged(List<FoodData> foodData) {
                        if(foodData != null){
                            binding.MessageIcon.setVisibility(View.GONE);
                            binding.MessageText.setVisibility(View.GONE);
                            countryFoodAdapter.setList(foodData);
                            countryFoodAdapter.notifyDataSetChanged();
                            countryFoodAdapter.SetOnClickLisiner(Position -> {
                                Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(DataManager.FoodID, Position);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(CountryFood.this);
                            });


                        }else {
                            binding.MessageIcon.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                            binding.MessageText.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void init_view() {
        binding.ToolbarTitle.setText(Country);
        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(CountryFood.this);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(CountryFood.this);
    }
}