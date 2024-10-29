package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.PopularRestaurentsViewHolder;
import com.example.mealmonkey.databinding.PopularrestaurentslayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodHomePopularRestaurentsAdapter extends RecyclerView.Adapter<PopularRestaurentsViewHolder> {

    private List<FoodData> foodModelList;
    private LayoutInflater layoutInflater;
    private PopularRestaurentsAdapter.Onclick Onclick;

    public void setFoodModelList(List<FoodData> foodModelList) {
        this.foodModelList = foodModelList;
    }

    @NonNull
    @Override
    public PopularRestaurentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        PopularrestaurentslayoutBinding binding = PopularrestaurentslayoutBinding.inflate(layoutInflater, parent, false);
        return new PopularRestaurentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRestaurentsViewHolder holder, int position) {
//  holder.binding.NameOfRestrount.setText(foodModelList.get(position).get());
        holder.binding.RatingCount.setText(String.valueOf(foodModelList.get(position).getRating()));
        holder.binding.FoodLocation.setText(foodModelList.get(position).getRestaurantsLocation());
        Picasso.get().load(foodModelList.get(position).getImageOne()).placeholder(R.drawable.foodofferplaceholder).into(holder.binding.FoodImage);

        holder.itemView.setOnClickListener(v ->{
            Onclick.Click(foodModelList.get(position).getFoodID());
        });
    }

    @Override
    public int getItemCount() {
        if(foodModelList == null){
            return 0;
        }else {
            return foodModelList.size();
        }
    }

    public interface Onclick{
        void Click(long Position);
    }
    public void SetOnClickLisiner(PopularRestaurentsAdapter.Onclick Onclick){
        this.Onclick = Onclick;
    }
}
