package com.example.mealmonkey.Recelerview.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.FoodportionlayoutBinding;

public class FoodSizeViewHOlder extends RecyclerView.ViewHolder {

    public FoodportionlayoutBinding binding;

    public FoodSizeViewHOlder(@NonNull FoodportionlayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
