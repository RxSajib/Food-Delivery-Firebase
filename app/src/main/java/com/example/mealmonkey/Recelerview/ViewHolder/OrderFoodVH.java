package com.example.mealmonkey.Recelerview.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.OrderfooditemBinding;

public class OrderFoodVH extends RecyclerView.ViewHolder {
    public OrderfooditemBinding binding;

    public OrderFoodVH(@NonNull OrderfooditemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
