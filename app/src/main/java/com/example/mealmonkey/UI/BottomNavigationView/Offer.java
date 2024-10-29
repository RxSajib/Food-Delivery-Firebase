package com.example.mealmonkey.UI.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Data.Model.FoodModel;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.Recelerview.Adapter.FoodMenuAdapter;
import com.example.mealmonkey.Recelerview.Adapter.PopularRestaurentsAdapter;
import com.example.mealmonkey.R;
import com.example.mealmonkey.UI.Activity.FoodDetails;
import com.example.mealmonkey.UI.Activity.FoodMenu;
import com.example.mealmonkey.databinding.OfferBinding;

import java.util.ArrayList;
import java.util.List;


public class Offer extends Fragment {

    private OfferBinding binding;
    private PopularRestaurentsAdapter adapter;
    private NetworkViewModel networkViewModel;


    public Offer() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.offer, container, false);
        networkViewModel = new ViewModelProvider(getActivity()).get(NetworkViewModel.class);

        GetDataFromServer();
        return binding.getRoot();
    }

    private void GetDataFromServer() {
        binding.FoodOfferRecyclerView.setHasFixedSize(true);
        binding.FoodOfferRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PopularRestaurentsAdapter();
        binding.FoodOfferRecyclerView.setAdapter(adapter);
        networkViewModel.GetOfferFood(DataManager.Offer, 20).observe(getActivity(), new Observer<List<FoodData>>() {
            @Override
            public void onChanged(List<FoodData> foodData) {
                if(foodData != null){
                    adapter.setFoodModelList(foodData);
                    adapter.notifyDataSetChanged();
                    adapter.SetOnClickLisiner(Position -> {
                        Intent intent = new Intent(getActivity(), FoodDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(DataManager.FoodID, Position);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(getActivity());
                    });
                }
            }
        });
    }


}