package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmonkey.Data.Model.OrderItem;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.OrderFoodItemVH;
import com.example.mealmonkey.databinding.OrderfooditemBinding;
import com.squareup.picasso.Picasso;
import java.util.List;

public class OrderFoodItemAdapter extends RecyclerView.Adapter<OrderFoodItemVH> {


    private List<OrderItem> orderItemList;

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public OrderFoodItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var  l = LayoutInflater.from(parent.getContext());
        var v = OrderfooditemBinding.inflate(l, parent, false);
        return new OrderFoodItemVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFoodItemVH holder, int position) {
        Picasso.get().load(orderItemList.get(position).getImageOne()).placeholder(R.color.carbon_black_6).into(holder.binding.ImageView);
        holder.binding.FoodTitle.setText(orderItemList.get(position).getFoodTitle());
        holder.binding.FoodMadeBy.setText(orderItemList.get(position).getFoodMadeBy());
        holder.binding.RatingNumber.setText(String.valueOf(orderItemList.get(position).getQuantity()));
        holder.binding.Price.setText("\u09F3 "+orderItemList.get(position).getFoodPrice());


        var st = orderItemList.get(position).getStatus();
        if(st.equals("Pending")){
            holder.binding.PendingBtn.setBackgroundResource(R.drawable.pendingbg);
            holder.binding.Status.setText("Pending");
        }else {
            holder.binding.Status.setText("Delivered");
            holder.binding.PendingBtn.setBackgroundResource(R.drawable.deliveredbg);
        }
    }

    @Override
    public int getItemCount() {
        if(orderItemList == null) {
            return 0;
        }else {
            return orderItemList.size();
        }
    }
}
