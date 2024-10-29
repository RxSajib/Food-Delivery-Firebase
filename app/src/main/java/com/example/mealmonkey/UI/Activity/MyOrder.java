package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.OrderModel;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.OrderAdapter;
import com.example.mealmonkey.databinding.MyorderBinding;

import java.util.List;

public class MyOrder extends AppCompatActivity {

    private MyorderBinding binding;
    private OrderAdapter orderAdapter;
    private NetworkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.myorder);
        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        orderAdapter = new OrderAdapter();

        InitView();
        GetData();
    }

    private void InitView() {
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setAdapter(orderAdapter);
        binding.BackButton.setOnClickListener(v -> {
            finish();
            Animatoo.animateSlideRight(MyOrder.this);
        });
    }

    private void GetData(){
        viewModel.GetOrder().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
                if(orderModels != null){
                    orderAdapter.setList(orderModels);
                    orderAdapter.notifyDataSetChanged();
                }else {
                    orderAdapter.setList(null);
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });

        orderAdapter.OnClickState(new OrderAdapter.OnClick() {
            @Override
            public void Click(OrderModel orderModel) {
                var intent = new Intent(getApplicationContext(), OrderProduct.class);
                intent.putExtra("Data", orderModel);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                Animatoo.animateSlideLeft(MyOrder.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(MyOrder.this);
    }
}