package com.example.mealmonkey.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.Recelerview.Adapter.FoodMenuAdapter;
import com.example.mealmonkey.databinding.BookmarkBinding;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookMark extends AppCompatActivity {

    private BookmarkBinding binding;
    private NetworkViewModel networkViewModel;
    private FoodMenuAdapter foodMenuAdapter;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bookmark);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);

        init_view();
        GETData_Server();
    }

    private void GETData_Server() {

        foodMenuAdapter = new FoodMenuAdapter();
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.RecyclerView.setAdapter(foodMenuAdapter);
        networkViewModel.GetBookMarkData().observe(this, new Observer<List<FoodData>>() {
            @Override
            public void onChanged(List<FoodData> foodData) {

                if (foodData != null) {
                    binding.RecyclerView.setVisibility(View.VISIBLE);
                    binding.MessageText.setVisibility(View.GONE);
                    binding.MessageIcon.setVisibility(View.GONE);
                    foodMenuAdapter.setList(foodData);
                    foodMenuAdapter.notifyDataSetChanged();
                    foodMenuAdapter.SetOnClickLisiner(Position -> {
                        Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(DataManager.FoodID, Position);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(BookMark.this);
                    });


                    foodMenuAdapter.SetOnCLongClickLisiner(new FoodMenuAdapter.OnLongClick() {
                        @Override
                        public void Click(long Position) {
                            removing(Position);
                        }
                    });


                } else {
                    binding.MessageText.setVisibility(View.VISIBLE);
                    binding.MessageIcon.setVisibility(View.VISIBLE);
                    foodMenuAdapter.notifyDataSetChanged();
                    binding.RecyclerView.setVisibility(View.GONE);
                }
            }


        });
    }

    private void removing(long foodID) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Delete(foodID, sDialog);
                    }
                })
                .show();

    }

    private void Delete(long foodID, SweetAlertDialog sDialog) {
        networkViewModel.BookMarkRemove(foodID).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    sDialog
                            .setTitleText("Deleted!")
                            .setContentText("Your file has been deleted!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                } else {
                    sDialog.dismissWithAnimation();

                }
            }
        });
    }

    private void init_view() {
        binding.BackButton.setOnClickListener(v -> {
            finish();
            Animatoo.animateSlideRight(BookMark.this);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(BookMark.this);
    }
}