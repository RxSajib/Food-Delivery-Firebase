package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.PaymentCardModel;
import com.example.mealmonkey.Network.ViewModel.RoomViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.ViewData.PaymentCardAdapter;
import com.example.mealmonkey.databinding.AddcardDialoagBinding;
import com.example.mealmonkey.databinding.CardremovedialogBinding;
import com.example.mealmonkey.databinding.PaymentdetailsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PaymentDetails extends AppCompatActivity {

    private PaymentdetailsBinding binding;
    private RoomViewModel viewModel;
    private PaymentCardModel model;
    private PaymentCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.paymentdetails);

        init_view();
    }

    private void init_view(){
        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(PaymentDetails.this);
        });

        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        binding.AddCardButton.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PaymentDetails.this);
            AddcardDialoagBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.addcard_dialoag, null, false);
            bottomSheetDialog.setContentView(binding.getRoot());

            bottomSheetDialog.show();



            binding.AddCardButton.setOnClickListener(view1 -> {
                String Cardnumber = binding.CardNumber.getText().toString().trim();
                String Month = binding.MonthEdittext.getText().toString().trim();
                String Year = binding.YearEdittext.getText().toString().trim();
                String SequrityCode = binding.SequrityCode.getText().toString().trim();
                String FirstName = binding.FirstName.getText().toString().trim();
                String LastName = binding.LastName.getText().toString().trim();

                if(Cardnumber.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Card number require", Toast.LENGTH_SHORT).show();
                }else if(Month.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Month require", Toast.LENGTH_SHORT).show();
                }else if(Year.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Year require", Toast.LENGTH_SHORT).show();
                }else if(SequrityCode.isEmpty()){
                    Toast.makeText(getApplicationContext(), "security code require", Toast.LENGTH_SHORT).show();
                }else if(FirstName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Firstname require", Toast.LENGTH_SHORT).show();
                }else if(LastName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Lastname require", Toast.LENGTH_SHORT).show();
                }
                else {


                    model = new PaymentCardModel( Cardnumber, Month, Year, SequrityCode, FirstName, LastName);
                    viewModel.InsertCardData(model);
                    Toast.makeText(getApplicationContext(), "Card added success", Toast.LENGTH_LONG).show();
                    bottomSheetDialog.dismiss();
                }
            });





        });

        binding.BackButton.setOnClickListener(view -> {
            finish();
        });

        viewcard();
    }

    private void viewcard(){
        adapter = new PaymentCardAdapter();
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setAdapter(adapter);

        viewModel.getCardData().observe(this, paymentCardModels -> {
            if(paymentCardModels != null){
                adapter.setCardModels(paymentCardModels);
                adapter.notifyDataSetChanged();
                adapter.OnRemoveLisiner(new PaymentCardAdapter.OnClick() {
                    @Override
                    public void Click(String CardNumber) {
                        MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(PaymentDetails.this, R.style.PauseDialog);
                        CardremovedialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.cardremovedialog, null, false);
                        Mbuilder.setView(binding.getRoot());

                        AlertDialog alertDialog = Mbuilder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.show();
                        binding.CardNumber.setText(CardNumber);
                        binding.DeleteCardButton.setOnClickListener(v ->{
                            viewModel.DeleteCardByNumber(CardNumber);
                            alertDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Delete success", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(PaymentDetails.this);
    }
}