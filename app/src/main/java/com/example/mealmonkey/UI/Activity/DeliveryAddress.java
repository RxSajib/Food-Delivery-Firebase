package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.Network.ViewModel.RoomViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.databinding.DeliveryaddressBinding;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddress extends AppCompatActivity {

    private DeliveryaddressBinding binding;
    private int Total;
    private NetworkViewModel networkViewModel;
    private RoomViewModel roomViewModel;
    private List<CartModel> cartModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.deliveryaddress);

        Total = getIntent().getIntExtra("Total", 0);
        binding.setMRPTotal(Total);
        binding.setTotalPrice(Total+60);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);



        init_view();
    }

    private void init_view(){
        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(DeliveryAddress.this);
        });


        binding.PlaceOrderBtn.setOnClickListener(view -> {
            var FirstName = binding.FirstNameInput.getText().toString().trim();
            var LastName = binding.LastNameInput.getText().toString().trim();
            var PhoneNumber = binding.PhoneNumberInput.getText().toString().trim();
            var StreetAddress = binding.AddressInput.getText().toString().trim();
            var TownCity = binding.TownCityInput.getText().toString().trim();
            var OrderNote = binding.OrderNoteInput.getText().toString().trim();
            var Area = binding.AreaInput.getText().toString().trim();

            if(FirstName.isEmpty()){
                binding.FirstNameInput.setError(getResources().getString(R.string.FirstNameEmpty));
            }else if(LastName.isEmpty()){
                binding.LastNameInput.setError(getResources().getString(R.string.LastNameEmpty));
            }else if(PhoneNumber.isEmpty()){
                binding.PhoneNumberInput.setError("Phone Number Empty");
            }else if(StreetAddress.isEmpty()){
                binding.AddressInput.setError(getResources().getString(R.string.StreetAddressEmpty));
            }else if(TownCity.isEmpty()){
                binding.TownCityInput.setError(getResources().getString(R.string.TownCityEmpty));
            }else if(Area.isEmpty()){
                binding.AreaInput.setError(getResources().getString(R.string.AreaEmpty));
            }else {
                roomViewModel.GetCartData().observe(this, new Observer<List<CartModel>>() {
                    @Override
                    public void onChanged(List<CartModel> cartModels) {
                        if(cartModels != null){
                            if(cartModels.size() > 0){
                                networkViewModel.OrderFood(FirstName, LastName, PhoneNumber, StreetAddress, TownCity, Area, OrderNote, cartModels)
                                        .observe(DeliveryAddress.this, new Observer<Boolean>() {
                                            @Override
                                            public void onChanged(Boolean aBoolean) {
                                                if(aBoolean){
                                                    roomViewModel.DeleteCart();
                                                    Toast.makeText(getApplicationContext(), "Order success", Toast.LENGTH_LONG).show();
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Failed to order", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }else {

                            }
                        }else {

                        }
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(DeliveryAddress.this);
    }
}