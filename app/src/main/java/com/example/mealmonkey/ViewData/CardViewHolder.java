package com.example.mealmonkey.ViewData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.databinding.PaymentcardDesignBinding;

public class CardViewHolder extends RecyclerView.ViewHolder {

    public PaymentcardDesignBinding binding;

    public CardViewHolder(@NonNull PaymentcardDesignBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


}
