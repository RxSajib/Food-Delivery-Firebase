package com.example.mealmonkey.Recelerview.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.OrderfooditemBinding;

public class OrderFoodItemVH extends RecyclerView.ViewHolder {

    public OrderfooditemBinding binding;

    public OrderFoodItemVH(@NonNull OrderfooditemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
