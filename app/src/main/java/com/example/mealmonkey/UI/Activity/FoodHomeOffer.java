package com.example.mealmonkey.UI.Activity;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.mealmonkey.Recelerview.Adapter.FoodMenuAdapter;
import com.example.mealmonkey.databinding.FoodofferBinding;

import java.util.List;

public class FoodHomeOffer extends AppCompatActivity {

    private FoodofferBinding binding;
    private NetworkViewModel networkViewModel;
    private FoodMenuAdapter foodMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.foodoffer);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        init_view();
        getDataServer();
    }

    private void getDataServer() {
        foodMenuAdapter = new FoodMenuAdapter();
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.RecyclerView.setAdapter(foodMenuAdapter);
        networkViewModel.GetOfferFood(DataManager.Offer, 20)
                .observe(this, new Observer<List<FoodData>>() {
                    @Override
                    public void onChanged(List<FoodData> foodData) {
                        if(foodData != null){
                            foodMenuAdapter.setList(foodData);
                            foodMenuAdapter.notifyDataSetChanged();
                            foodMenuAdapter.SetOnClickLisiner(Position -> {
                                Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(DataManager.FoodID, Position);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(FoodHomeOffer.this);
                            });
                            foodMenuAdapter.SetOnCLongClickLisiner(new FoodMenuAdapter.OnLongClick() {
                                @Override
                                public void Click(long Position) {
                                    
                                }
                            });
                        }else {

                        }
                    }
                });
    }

    private void init_view(){
        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(FoodHomeOffer.this);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(FoodHomeOffer.this);
    }
}