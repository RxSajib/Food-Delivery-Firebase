package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.FoodSize;
import com.example.mealmonkey.Recelerview.ViewHolder.FoodSizeViewHOlder;
import com.example.mealmonkey.databinding.FoodportionlayoutBinding;

import java.util.List;

public class FoodSizeAdapter extends RecyclerView.Adapter<FoodSizeViewHOlder> {

    private List<FoodSize> foodSizeList;

    public FoodSizeAdapter(List<FoodSize> foodSizeList) {
        this.foodSizeList = foodSizeList;
    }
    private LayoutInflater layoutInflater;

    @NonNull
    @Override
    public FoodSizeViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        FoodportionlayoutBinding binding = FoodportionlayoutBinding.inflate(layoutInflater, parent, false);
        return new FoodSizeViewHOlder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodSizeViewHOlder holder, int position) {
        holder.binding.SizeText.setText(foodSizeList.get(position).getSizeTitle());
        holder.binding.PriceText.setText(String.valueOf("\u09F3 "+String.valueOf(foodSizeList.get(position).getPrice())));
    }

    @Override
    public int getItemCount() {
        if(foodSizeList == null){
            return 0;
        }else {
            return foodSizeList.size();
        }
    }
}
