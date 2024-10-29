package com.example.mealmonkey.UI.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Network.ViewModel.RoomViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.CartAdapter;
import com.example.mealmonkey.databinding.AddcartBinding;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AddCart extends AppCompatActivity {

    private AddcartBinding binding;
    private RoomViewModel roomViewModel;
    private CartAdapter cartAdapter;
    private int MRPTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.addcart);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        cartAdapter = new CartAdapter();

        init_view();
        GetCartData();
    }

    private void GetCartData(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                roomViewModel.DeleteCartItem(cartAdapter.getList().get(viewHolder.getAdapterPosition()).getFoodID());
                cartAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Delete success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addActionIcon(R.drawable.ic_delete_sweep_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        roomViewModel.GetCartData().observe(this, new Observer<List<CartModel>>() {
            @Override
            public void onChanged(List<CartModel> cartModels) {
                if(cartModels != null){
                    if(cartModels.size() > 0){
                        MRPTotal = 0;
                        cartAdapter.setList(cartModels);
                        cartAdapter.notifyDataSetChanged();
                        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.RecyclerView);

                        binding.MessageText.setVisibility(View.GONE);
                        binding.MessageIcon.setVisibility(View.GONE);
                        binding.PaymentDetailsText.setVisibility(View.VISIBLE);
                        binding.PriceBox.setVisibility(View.VISIBLE);
                        binding.TotalSavingBox.setVisibility(View.VISIBLE);
                        binding.ProcessToCheckOut.setVisibility(View.VISIBLE);
                        binding.TotalSaving.setVisibility(View.VISIBLE);
                        binding.CheckOut.setVisibility(View.VISIBLE);

                        for (var data : cartModels) {
                            MRPTotal = data.getTotalPrice() + MRPTotal;
                            binding.setMRPTotal(String.valueOf(MRPTotal));
                            binding.setTotalAmount(String.valueOf(MRPTotal+60));
                        }


                        binding.ProcessToCheckOut.setOnClickListener(view -> {
                           var i = new Intent(getApplicationContext(), DeliveryAddress.class);
                           i.putExtra("Total", MRPTotal);
                           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(i);
                           Animatoo.animateSlideLeft(AddCart.this);
                        });
                    }else {
                        cartAdapter.setList(null);
                        cartAdapter.notifyDataSetChanged();

                        binding.TotalSaving.setVisibility(View.GONE);
                        binding.PaymentDetailsText.setVisibility(View.GONE);
                        binding.PriceBox.setVisibility(View.GONE);
                        binding.TotalSavingBox.setVisibility(View.GONE);
                        binding.ProcessToCheckOut.setVisibility(View.GONE);
                        binding.CheckOut.setVisibility(View.GONE);

                        binding.MessageIcon.setVisibility(View.VISIBLE);
                        binding.MessageText.setVisibility(View.VISIBLE);
                    }
                }else {
                    cartAdapter.setList(null);
                    cartAdapter.notifyDataSetChanged();


                    binding.TotalSaving.setVisibility(View.GONE);
                    binding.PaymentDetailsText.setVisibility(View.GONE);
                    binding.PriceBox.setVisibility(View.GONE);
                    binding.TotalSavingBox.setVisibility(View.GONE);
                    binding.ProcessToCheckOut.setVisibility(View.GONE);
                    binding.CheckOut.setVisibility(View.GONE);

                    binding.MessageIcon.setVisibility(View.VISIBLE);
                    binding.MessageText.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void init_view(){
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setAdapter(cartAdapter);

        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(AddCart.this);
        });



    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(AddCart.this);
    }
}