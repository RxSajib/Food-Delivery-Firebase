package com.example.mealmonkey.UI.BottomNavigationView;

import android.content.Intent;
import android.graphics.Color;
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

import com.airbnb.lottie.L;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.CovidCountry;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.EmailValidationResponse;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Data.Model.FoodModel;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.FoodHomePopularRestaurentsAdapter;
import com.example.mealmonkey.Recelerview.Adapter.FoodMenuAdapter;
import com.example.mealmonkey.Recelerview.Adapter.MostPopularAdapter;
import com.example.mealmonkey.Recelerview.Adapter.PopularRestaurentsAdapter;
import com.example.mealmonkey.UI.Activity.CountryFood;
import com.example.mealmonkey.UI.Activity.FoodDetails;
import com.example.mealmonkey.UI.Activity.FoodHomeOffer;
import com.example.mealmonkey.UI.Activity.FoodMenu;
import com.example.mealmonkey.UI.Activity.FoodSearch;
import com.example.mealmonkey.databinding.FoodhomeBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FoodHome extends Fragment {

    private FoodhomeBinding binding;
    private MostPopularAdapter mostPopularAdapter;
    private List<FoodModel> foodModelList = new ArrayList<>();

    private FoodHomePopularRestaurentsAdapter popularRestaurentsAdapter;
    private List<FoodModel> popular_restruntlist = new ArrayList<>();

    private FoodMenuAdapter foodMenuAdapter;
    private List<FoodModel> newfoodlist = new ArrayList<>();
    private NetworkViewModel networkViewModel;


    public FoodHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.foodhome, container, false);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        set_data();
        init_view();
        PopularRestaurentsData();
        NewFoodData();
        getcoviddata();
        mailvalidation();

        return binding.getRoot();
    }

    private void mailvalidation(){
        networkViewModel.EmailValidation(DataManager.EmailValidationApiKey, "..@gmail.com")
                .observe(getActivity(), new Observer<EmailValidationResponse>() {
                    @Override
                    public void onChanged(EmailValidationResponse emailValidationResponse) {
                        if(emailValidationResponse != null){
                            Log.d("TAG", emailValidationResponse.getEmailValidation().getText());
                          //  Toast.makeText(getActivity(), String.valueOf(emailValidationResponse.getEmailValidation().isValue()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getcoviddata() {




    }

    private void NewFoodData() {
        binding.NewFoodRecyclerView.setHasFixedSize(true);
        binding.NewFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodMenuAdapter = new FoodMenuAdapter();
        binding.NewFoodRecyclerView.setAdapter(foodMenuAdapter);
        networkViewModel.SearchFood(DataManager.All, 5).observe(getActivity(), new Observer<List<FoodData>>() {
            @Override
            public void onChanged(List<FoodData> foodData) {
                if (foodData != null) {
                    foodMenuAdapter.setList(foodData);
                    foodMenuAdapter.notifyDataSetChanged();
                    foodMenuAdapter.SetOnClickLisiner(Position -> {
                        Intent intent = new Intent(getActivity(), FoodDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(DataManager.FoodID, Position);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(getActivity());
                    });

                    foodMenuAdapter.SetOnCLongClickLisiner(new FoodMenuAdapter.OnLongClick() {
                        @Override
                        public void Click(long Position) {

                        }
                    });
                } else {

                }

            }
        });
    }

    private void PopularRestaurentsData() {
        binding.PopulatRestruntRecyclerView.setHasFixedSize(true);
        popularRestaurentsAdapter = new FoodHomePopularRestaurentsAdapter();
        binding.PopulatRestruntRecyclerView.setAdapter(popularRestaurentsAdapter);
        networkViewModel.GetPromotionFood(DataManager.Promotion, 2).observe(getActivity(), new Observer<List<FoodData>>() {
            @Override
            public void onChanged(List<FoodData> foodData) {
                if (foodData != null) {
                    popularRestaurentsAdapter.setFoodModelList(foodData);
                    popularRestaurentsAdapter.notifyDataSetChanged();
                    popularRestaurentsAdapter.SetOnClickLisiner(Position -> {
                        Intent intent = new Intent(getActivity(), FoodDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(DataManager.FoodID, Position);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(getActivity());
                    });
                } else {

                }
            }
        });
    }

    private void init_view() {
        binding.FoodOfferButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FoodHomeOffer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
        binding.SearchInput.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FoodSearch.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
        binding.SriLankaButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CountryFood.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(DataManager.Data, DataManager.Sri_Lankan);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
        binding.ItalianButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CountryFood.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(DataManager.Data, DataManager.Italian);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
        binding.IndiaButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CountryFood.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(DataManager.Data, DataManager.India);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
    }


    private void set_data() {
        FoodModel one = new FoodModel("Big KFC", "Indian Food", "256", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXvqkIqDSXA1vHlsNkglefziccelVW1VczsLM9l2_Qi7w0RkoZnajEFEIIJKYygoFWQlE&usqp=CAU", 4, 5, "KCH Star");
        FoodModel two = new FoodModel("Chicken Biryani", "Bangla Food", "296", "https://images.food52.com/McqpjxUiMekhfX6Rsq7wuuSoz0g=/2016x1344/filters:format(webp)/d815e816-4664-472e-990b-d880be41499f--chicken-biryani-recipe.jpg", 6, 50, "Food Star");
        FoodModel three = new FoodModel("Egg Biryani", "Bangla Food", "96", "https://www.watscooking.com/images/dish/normal/1041.jpg", 8, 80, "Food Star");

        foodModelList.add(one);
        foodModelList.add(two);
        foodModelList.add(three);
        mostPopularAdapter = new MostPopularAdapter(foodModelList);
        binding.PopularRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.PopularRecyclerView.setLayoutManager(layoutManager);
        binding.PopularRecyclerView.setAdapter(mostPopularAdapter);
    }
}