package com.example.mealmonkey.ViewData;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.PaymentCardModel;
import com.example.mealmonkey.databinding.PaymentcardDesignBinding;
import com.example.mealmonkey.databinding.PaymentdetailsBinding;

import java.util.List;

public class PaymentCardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<PaymentCardModel> cardModels;

    public void setCardModels(List<PaymentCardModel> cardModels) {
        this.cardModels = cardModels;
    }
    private OnClick OnClick;

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PaymentcardDesignBinding binding = PaymentcardDesignBinding.inflate(layoutInflater, parent, false);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.binding.CardNumberID.setText(String.valueOf(cardModels.get(position).getCardNumber()));

        holder.binding.DeleteCardButton.setOnClickListener(v ->{
            OnClick.Click(cardModels.get(position).getCardNumber());
        });
    }

    @Override
    public int getItemCount() {
        if(cardModels == null){
            return 0;
        }else {
          return  cardModels.size();
        }
    }

    public interface OnClick{
        void Click(String CardNumber);
    }
    public void OnRemoveLisiner(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
