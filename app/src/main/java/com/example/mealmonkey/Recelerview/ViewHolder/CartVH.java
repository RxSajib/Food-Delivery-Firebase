package com.example.mealmonkey.Recelerview.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.CartitemBinding;

public class CartVH extends RecyclerView.ViewHolder {

    public CartitemBinding binding;

    public CartVH(@NonNull CartitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
