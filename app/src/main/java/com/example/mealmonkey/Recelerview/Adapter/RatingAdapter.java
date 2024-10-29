package com.example.mealmonkey.Recelerview.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.RatingNumberModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.ViewHolder.RatingVH;
import com.example.mealmonkey.databinding.RatingitemBinding;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingVH>{


    private List<RatingNumberModel> list;
    public List<RatingNumberModel> getList() {
        return list;
    }
    public void setList(List<RatingNumberModel> list) {
        this.list = list;
    }

    private OnClick OnClick;
    private int row_index = -1;

    @NonNull
    @Override
    public RatingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var v = RatingitemBinding.inflate(l, parent, false);
        return new RatingVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingVH holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.RCount.setText(String.valueOf(list.get(position).getCount()));

        holder.itemView.setOnClickListener(view -> {
            OnClick.Click(list.get(position));
            row_index= position;
            notifyDataSetChanged();
        });
        if(row_index==position){
          //  holder.binding.RCount.setTextColor(R.color.white);
            holder.binding.Root.setBackgroundResource(R.drawable.ratingactive);

        } else {
          //  holder.binding.RCount.setTextColor(R.color.black);
            holder.binding.Root.setBackgroundResource(R.drawable.ratinginactive);
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

    public interface  OnClick{
        void Click(RatingNumberModel ratingNumberModel);
    }
    public void OnClickState(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
