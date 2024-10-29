package com.example.mealmonkey.Recelerview.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.FooditemBinding;

public class FoodMenuViewHolder extends RecyclerView.ViewHolder {

    public FooditemBinding binding;

    public FoodMenuViewHolder(@NonNull FooditemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}