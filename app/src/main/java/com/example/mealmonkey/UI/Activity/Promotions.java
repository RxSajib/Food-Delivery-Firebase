package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Data.Model.FoodModel;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.FoodMenuAdapter;
import com.example.mealmonkey.databinding.PromotionsBinding;

import java.util.ArrayList;
import java.util.List;

public class Promotions extends AppCompatActivity {

    private PromotionsBinding binding;
    private FoodMenuAdapter foodMenuAdapter;
    private NetworkViewModel networkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.promotions);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        init_view();
        getdata_from_server();

    }

    private void getdata_from_server() {
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(Promotions.this));
        foodMenuAdapter = new FoodMenuAdapter();
        binding.RecyclerView.setAdapter(foodMenuAdapter);
        networkViewModel.GetPromotionFood(DataManager.Promotion, 20).observe(this, new Observer<List<FoodData>>() {
            @Override
            public void onChanged(List<FoodData> foodData) {
                if(foodData != null){
                    binding.MessageIcon.setVisibility(View.GONE);
                    binding.MessageText.setVisibility(View.GONE);
                    foodMenuAdapter.setList(foodData);
                    foodMenuAdapter.notifyDataSetChanged();
                    foodMenuAdapter.SetOnClickLisiner(Position -> {
                        Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(DataManager.FoodID, Position);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(Promotions.this);
                    });
                    foodMenuAdapter.SetOnCLongClickLisiner(new FoodMenuAdapter.OnLongClick() {
                        @Override
                        public void Click(long Position) {

                        }
                    });
                }else {
                    binding.MessageIcon.setVisibility(View.VISIBLE);
                    binding.MessageText.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void init_view(){
        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(Promotions.this);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(Promotions.this);
    }
}