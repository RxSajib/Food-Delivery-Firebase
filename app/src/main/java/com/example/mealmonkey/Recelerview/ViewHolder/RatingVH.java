package com.example.mealmonkey.Recelerview.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmonkey.databinding.RatingitemBinding;

public class RatingVH extends RecyclerView.ViewHolder {

    public RatingitemBinding binding;

    public RatingVH(@NonNull RatingitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
