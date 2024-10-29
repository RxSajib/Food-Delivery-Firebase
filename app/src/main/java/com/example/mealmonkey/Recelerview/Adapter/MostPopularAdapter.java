package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.FoodModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.MostPopularViewHolder;
import com.example.mealmonkey.databinding.MostpopularlayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder> {

    private LayoutInflater layoutInflater;
    private List<FoodModel> foodModelList;

    public MostPopularAdapter(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }

    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        MostpopularlayoutBinding binding = MostpopularlayoutBinding.inflate(layoutInflater, parent, false);
        return new MostPopularViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MostPopularViewHolder holder, int position) {
        Picasso.get().load(foodModelList.get(position).getFoodImage()).placeholder(R.drawable.most_popularfoodplaceholder).placeholder(R.drawable.mostpopularrestrunts_placeholder).into(holder.binding.FoodImage);
        holder.binding.FoodMadeBy.setText(foodModelList.get(position).getFoodMadeBy());
        holder.binding.FoodTitle.setText(foodModelList.get(position).getFoodName());
        holder.binding.FoodLocation.setText(foodModelList.get(position).getFoodLocation());
        holder.binding.Rating.setRating(foodModelList.get(position).getRating()/2);
        holder.binding.RatingNumber.setText(String.valueOf(foodModelList.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        if(foodModelList == null){
            return 0;
        }else {
            return foodModelList.size();
        }
    }
}
