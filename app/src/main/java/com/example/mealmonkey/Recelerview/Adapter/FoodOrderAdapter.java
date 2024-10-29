package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.OrderItem;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.OrderFoodVH;
import com.example.mealmonkey.databinding.OrderfooditemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodOrderAdapter extends RecyclerView.Adapter<OrderFoodVH> {

    private List<OrderItem> list;

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }
    private OnReview OnReview;

    @NonNull
    @Override
    public OrderFoodVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var  l = LayoutInflater.from(parent.getContext());
        var v = OrderfooditemBinding.inflate(l, parent, false);
        return new OrderFoodVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFoodVH holder, int position) {
        Picasso.get().load(list.get(position).getImageOne()).placeholder(R.color.carbon_black_6).into(holder.binding.ImageView);
        holder.binding.FoodTitle.setText(list.get(position).getFoodTitle());
        holder.binding.FoodMadeBy.setText(list.get(position).getFoodMadeBy());
        holder.binding.RatingNumber.setText(String.valueOf(list.get(position).getQuantity()));
        holder.binding.Price.setText(String.valueOf("\u09F3 "+list.get(position).getFoodPrice()));

        var st = list.get(position).getStatus();
        if(st.equals("Pending")){
            holder.binding.PendingBtn.setBackgroundResource(R.drawable.pendingbg);
            holder.binding.Status.setText("Pending");
        }else {
            holder.binding.Status.setText("Review");
            holder.binding.PendingBtn.setBackgroundResource(R.drawable.deliveredbg);
            holder.binding.PendingBtn.setOnClickListener(view -> {
                OnReview.Click(list.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }else {
            return list.size();
        }
    }

    public interface OnReview{
        void Click(OrderItem orderItem);
    }
    public void OnClickReviewState(OnReview OnReview){
        this.OnReview = OnReview;
    }
}
