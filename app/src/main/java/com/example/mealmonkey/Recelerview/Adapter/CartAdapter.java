package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.CartVH;
import com.example.mealmonkey.databinding.CartitemBinding;
import com.squareup.picasso.Picasso;
import java.util.List;



public class CartAdapter extends RecyclerView.Adapter<CartVH> {

    private List<CartModel> list;

    public List<CartModel> getList() {
        return list;
    }

    public void setList(List<CartModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       var l = LayoutInflater.from(parent.getContext());
       var v = CartitemBinding.inflate(l, parent, false);
       return new CartVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        Picasso.get().load(list.get(position).getImageOne()).into(holder.binding.ImageView);
        holder.binding.FoodRating.setRating(list.get(position).getRating()/2);
        holder.binding.FoodTitle.setText(list.get(position).getFoodTitle());
        holder.binding.FoodMadeBy.setText(list.get(position).getFoodMadeBy());
     //   holder.binding.FoodLocation.setText(list.get(position).getRestaurantsLocation());
        holder.binding.RatingNumber.setText(String.valueOf(list.get(position).getRating()));
        holder.binding.Price.setText(String.valueOf(list.get(position).getTotalPrice()));

    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }else {
            return list.size();
        }
    }
}
