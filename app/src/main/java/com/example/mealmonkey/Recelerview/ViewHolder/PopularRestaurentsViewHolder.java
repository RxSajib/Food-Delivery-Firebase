package com.example.mealmonkey.Recelerview.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.PopularrestaurentslayoutBinding;

public class PopularRestaurentsViewHolder extends RecyclerView.ViewHolder {

    public PopularrestaurentslayoutBinding binding;

    public PopularRestaurentsViewHolder(@NonNull PopularrestaurentslayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
