package com.example.mealmonkey.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.FoodMenuAdapter;
import com.example.mealmonkey.databinding.FoodsearchBinding;

import java.util.List;

public class FoodSearch extends AppCompatActivity {

    private FoodsearchBinding binding;
    private NetworkViewModel networkViewModel;
    private FoodMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.foodsearch);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        init_view();
    }


    private void init_view() {
        binding.FoodSearchRecyclerView.setHasFixedSize(true);
        binding.FoodSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new FoodMenuAdapter();
        binding.FoodSearchRecyclerView.setAdapter(adapter);
        binding.BackButton.setOnClickListener(v -> {
            finish();
            Animatoo.animateSlideRight(FoodSearch.this);
        });

        binding.SearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newtext = s.toString();
                if (newtext.isEmpty()) {
                    get_data(DataManager.All);
                } else {
                    get_data(newtext);
                }
            }
        });
        get_data(DataManager.All);

    }

    private void get_data(String Food) {
        networkViewModel.SearchFood(Food, 20).observe(this, new Observer<List<FoodData>>() {
            @Override
            public void onChanged(List<FoodData> foodData) {
                if (foodData != null) {
                    adapter.setList(foodData);
                    adapter.notifyDataSetChanged();
                    adapter.SetOnClickLisiner(Position -> {
                        Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(DataManager.FoodID, Position);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(FoodSearch.this);
                    });
                    adapter.SetOnCLongClickLisiner(new
                                                           FoodMenuAdapter.OnLongClick() {
                                                               @Override
                                                               public void Click(long Position) {

                                                               }
                                                           });
                } else {

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(FoodSearch.this);
    }
}