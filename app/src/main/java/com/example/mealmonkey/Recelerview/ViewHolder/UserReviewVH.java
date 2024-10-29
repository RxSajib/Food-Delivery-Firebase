package com.example.mealmonkey.Recelerview.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.UserratingitemBinding;

public class UserReviewVH extends RecyclerView.ViewHolder {

    public UserratingitemBinding binding;

    public UserReviewVH(@NonNull UserratingitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
