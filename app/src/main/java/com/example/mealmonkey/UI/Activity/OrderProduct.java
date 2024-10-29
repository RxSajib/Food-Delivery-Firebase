package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.RatingNumberData;
import com.example.mealmonkey.Data.Model.OrderItem;
import com.example.mealmonkey.Data.Model.OrderModel;
import com.example.mealmonkey.Data.Model.RatingNumberModel;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.FoodOrderAdapter;
import com.example.mealmonkey.Recelerview.Adapter.RatingAdapter;
import com.example.mealmonkey.UI.utils.Utils;
import com.example.mealmonkey.databinding.FeedbackdialogBinding;
import com.example.mealmonkey.databinding.OrderproductBinding;

import java.util.List;

import javax.inject.Inject;

import okhttp3.internal.Util;

public class OrderProduct extends AppCompatActivity {

    private OrderproductBinding binding;
    private OrderModel orderModel;
    private NetworkViewModel viewModel;
    private FoodOrderAdapter foodOrderAdapter;
    private RatingAdapter ratingAdapter = new RatingAdapter();
    private int RatingNumber;
   // private IOSDialog iosDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.orderproduct);

        foodOrderAdapter = new FoodOrderAdapter();
        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        orderModel = (OrderModel) getIntent().getSerializableExtra("Data");


        InitView();
        GetData();
    }

    private void ShowDialoag() {
        Utils.showProgressDialog(this);
      /*  iosDialog = new IOSDialog.Builder(OrderProduct.this)
                .setTitle("loading")
                .setDimAmount(3)
                .setSpinnerDuration(120)
                .setMessageContentGravity(Gravity.END)
                .setCancelable(false)
                .setMessageContent("Login your Account")
                .build();

        iosDialog.show();*/
    }
    private void GetData(){
        viewModel.GetOrderItem(String.valueOf(orderModel.getID())).observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                if(orderItems != null){
                    foodOrderAdapter.setList(orderItems);
                    foodOrderAdapter.notifyDataSetChanged();
                }else {
                    foodOrderAdapter.setList(null);
                    foodOrderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void InitView() {
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setAdapter(foodOrderAdapter);
        binding.BackButton.setOnClickListener(v -> {
            finish();
            Animatoo.animateSlideRight(OrderProduct.this);
        });

        foodOrderAdapter.OnClickReviewState(orderItem -> {
            ReviewDialog(orderItem);
        });
    }

    private void ReviewDialog(OrderItem orderItem){
        var d = new AlertDialog.Builder(OrderProduct.this);
        var reviewdialog = FeedbackdialogBinding.inflate(getLayoutInflater(),null, false);
        d.setView(reviewdialog.getRoot());
        reviewdialog.RecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        reviewdialog.RecyclerView.setLayoutManager(layoutManager);
        ratingAdapter.setList(RatingNumberData.GetRatingData());
        reviewdialog.RecyclerView.setAdapter(ratingAdapter);

        var x = d.create();
        x.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        x.show();

        ratingAdapter.OnClickState(new RatingAdapter.OnClick() {
            @Override
            public void Click(RatingNumberModel ratingNumberModel) {
                RatingNumber = ratingNumberModel.getCount();
            }
        });

        reviewdialog.SendBtn.setOnClickListener(view -> {
            var Comment = reviewdialog.CommentInput.getText().toString().trim();
            if(Comment.isEmpty()){
                reviewdialog.CommentInput.setError("Empty");
            }else if(RatingNumber == 0){
                Toast.makeText(getApplicationContext(), "Select number of rating", Toast.LENGTH_LONG).show();
            }else {
                ShowDialoag();
                viewModel.SendRating(String.valueOf(orderItem.getFoodID()), Comment, RatingNumber).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            Utils.hideProgressDialog();
                           // iosDialog.dismiss();
                            reviewdialog.CommentInput.setText(null);
                        }else {
                            Utils.hideProgressDialog();
                         //   iosDialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(OrderProduct.this);
    }
}