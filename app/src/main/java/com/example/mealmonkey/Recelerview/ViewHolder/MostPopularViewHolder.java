package com.example.mealmonkey.Recelerview.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.MostpopularlayoutBinding;

public class MostPopularViewHolder extends RecyclerView.ViewHolder {

    public MostpopularlayoutBinding binding;

    public MostPopularViewHolder(@NonNull MostpopularlayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
