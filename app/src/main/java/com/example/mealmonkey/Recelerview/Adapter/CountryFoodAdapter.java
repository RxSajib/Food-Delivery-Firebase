package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.FoodMenuViewHolder;
import com.example.mealmonkey.databinding.FooditemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryFoodAdapter extends RecyclerView.Adapter<FoodMenuViewHolder> {
    private List<FoodData> list;
    private LayoutInflater layoutInflater;
    private FoodMenuAdapter.Onclick Onclick;

    public void setList(List<FoodData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FoodMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        FooditemBinding binding = FooditemBinding.inflate(layoutInflater, parent, false);
        return new FoodMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodMenuViewHolder holder, int position) {
        holder.binding.FoodRating.setText(list.get(position).getFoodPrice()+" ৳");
        holder.binding.FoodTitle.setText(list.get(position).getFoodTitle());
        holder.binding.FoodMadeBy.setText(list.get(position).getFoodMadeBy());
        holder.binding.FoodLocation.setText(list.get(position).getRestaurantsLocation());
        holder.binding.RatingNumber.setText(String.valueOf(list.get(position).getRating()));
        holder.binding.FoodNumberOFSell.setText("Delivery charge included");
        Picasso.get().load(list.get(position).getImageOne()).placeholder(R.drawable.foodmenu_placeholder).into(holder.binding.ImageView);

        holder.itemView.setOnClickListener(v ->{
            Onclick.Click(list.get(position).getFoodID());
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }
    public interface Onclick{
        void Click(long Position);
    }
    public void SetOnClickLisiner(FoodMenuAdapter.Onclick Onclick){
        this.Onclick = Onclick;
    }
}
