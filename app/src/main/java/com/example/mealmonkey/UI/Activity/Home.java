package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.UI.BottomNavigationView.FoodHome;
import com.example.mealmonkey.UI.BottomNavigationView.Menu;
import com.example.mealmonkey.UI.BottomNavigationView.More;
import com.example.mealmonkey.UI.BottomNavigationView.Offer;
import com.example.mealmonkey.UI.BottomNavigationView.Profile;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.databinding.ExitdialogBinding;
import com.example.mealmonkey.databinding.HomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;

public class Home extends AppCompatActivity {

    private HomeBinding binding;
    private NetworkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.home);

        init_view();
        getTimeFromAndroid();
        gotomenu(new Menu());
    }


    private void getTimeFromAndroid() {
        Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();

        if(hours>=1 || hours<=12){
            binding.ToolbarTitle.setText("Good Morning");
        }else if(hours>=12 || hours<=16){
            binding.ToolbarTitle.setText("Good Afternoon");
        }else if(hours>=16 || hours<=21){
            binding.ToolbarTitle.setText("Good Evening");
        }else if(hours>=21 || hours<=24){
            binding.ToolbarTitle.setText("Good Night");
        }
    }

    private void init_view(){
        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        binding.BottomNavView.setBackground(null);
        binding.BottomNavView.getMenu().getItem(2).setEnabled(false);

        binding.BottomNavView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.MenuID){
                gotomenu(new Menu());
            }
            if(item.getItemId() == R.id.Offer){
                gotooffer(new Offer());
            }
            if(item.getItemId() == R.id.Profile){
                gotoprofile(new Profile());
            }
            if(item.getItemId() == R.id.More){
                gotomore(new More());
            }

            return true;
        });

        binding.FabButton.setOnClickListener(view -> {
            goto_foodhome(new FoodHome());
        });

        binding.CartButton.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), AddCart.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(Home.this);
        });
    }

    private void gotomenu(Fragment fragment){
        if(fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.MainFrameLayout, fragment);
            transaction.commit();
            binding.ToolbarTitle.setText(DataManager.Menu);
        }
    }
    private void gotooffer(Fragment fragment){
        if(fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.MainFrameLayout, fragment);
            transaction.commit();
            binding.ToolbarTitle.setText(DataManager.LatestOffer);
        }
    }
    private void gotoprofile(Fragment fragment){
        if(fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.MainFrameLayout, fragment);
            transaction.commit();
            binding.ToolbarTitle.setText(DataManager.Profile);
        }
    }
    private void gotomore(Fragment fragment){
        if(fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.MainFrameLayout, fragment);
            transaction.commit();
            binding.ToolbarTitle.setText(DataManager.More);
        }
    }

    private void goto_foodhome(Fragment fragment){
        if(fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.MainFrameLayout, fragment);
            transaction.commit();
            binding.ToolbarTitle.setText("Good morning Sajib");
            binding.ToolbarTitle.setTextSize(18f);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.CheckUserLogin().observe(this, integer -> {
            if(integer == ResponseCode.ErrorCODE){
                goto_getstart();
            }
        });
    }

    private void goto_getstart(){
        Intent intent = new Intent(getApplicationContext(), GetStart.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(Home.this, R.style.PauseDialog);
        ExitdialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.exitdialog, null, false);
        Mbuilder.setView(binding.getRoot());

        AlertDialog alertDialog = Mbuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        binding.CloseButton.setOnClickListener(v ->{
            alertDialog.dismiss();
        });
        binding.ExitButton.setOnClickListener(v ->{
            alertDialog.dismiss();
            finish();
        });
    }
}