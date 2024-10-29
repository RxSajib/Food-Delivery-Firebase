package com.example.mealmonkey.UI.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.UI.Activity.Beverage;
import com.example.mealmonkey.UI.Activity.Desserts;
import com.example.mealmonkey.UI.Activity.FoodMenu;
import com.example.mealmonkey.UI.Activity.FoodSearch;
import com.example.mealmonkey.UI.Activity.Promotions;
import com.example.mealmonkey.databinding.MenuBinding;


public class Menu extends Fragment {

    private MenuBinding binding;
    private NetworkViewModel networkViewModel;

    public Menu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.menu, container, false);
        networkViewModel = new ViewModelProvider(getActivity()).get(NetworkViewModel.class);
        inti_view();
        getFoodSize();
        return binding.getRoot();
    }

    private void getFoodSize(){
             networkViewModel.GetFoodItemSize(DataManager.Food).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.FoodItemSize.setText(String.valueOf(integer)+" Item");

            }
        });
       networkViewModel.GetBeveragesSize(DataManager.Beverages).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.BeveragesCount.setText(String.valueOf(integer)+" Item");
            }
        });

        networkViewModel.GetDessertsSize(DataManager.Desserts).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.DesertsCount.setText(String.valueOf(integer)+" Item");
            }
        });
        networkViewModel.GetPromotionSize(DataManager.Promotion).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.PromotionsCount.setText(String.valueOf(integer)+" Item");
            }
        });
    }

    private void inti_view() {
        binding.SearchFood.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), FoodSearch.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
        binding.FoodCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FoodMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });

        binding.BeveragesCard.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Beverage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });

        binding.DessertsCard.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Desserts.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });


        binding.PromotionsCard.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Promotions.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
    }


}