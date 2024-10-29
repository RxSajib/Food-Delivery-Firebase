package com.example.mealmonkey.UI.BottomNavigationView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.example.mealmonkey.UI.Activity.SplashScreen;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.UI.utils.Utils;
import com.example.mealmonkey.databinding.LogoutdialoagBinding;
import com.example.mealmonkey.databinding.PhotocapturedialoagBinding;
import com.example.mealmonkey.databinding.ProfileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;


public class Profile extends Fragment {

    private ProfileBinding binding;
    private NetworkViewModel viewModel;
  //  private IOSDialog iosDialog;
    private static final int IMAGECODE = 100;
    private Uri Imageuri;
    private String ImageDownloadUri;
    private AlertDialog alertDialog;
    private static final int PERMISSIONCODE = 100;
    private DatePickerDialog.OnDateSetListener setListener;
    private String Birthday;

    public Profile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile, container, false);

        init_view();
        return binding.getRoot();
    }

    private void init_view() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        binding.SignOut.setOnClickListener(view -> {
            MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(getActivity(), R.style.PauseDialog);
            LogoutdialoagBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.logoutdialoag, null, false);
            Mbuilder.setView(binding.getRoot());

            AlertDialog alertDialog = Mbuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            binding.StayButton.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });

            binding.ContinueButton.setOnClickListener(view12 -> {
                viewModel.LogoutAccount().observe(getActivity(), integer -> {
                    if (integer == ResponseCode.SUCCESSCODE) {
                        goto_splashscreen();
                    }
                });
            });

        });

        viewModel.getCurrentUser().observe(getActivity(), user -> {
            if (user != null) {
                binding.NameBox.getEditText().setText(user.getName());
                binding.EmailBox.getEditText().setText(user.getEmail());
                binding.Address.getEditText().setText(user.getAddress());
                binding.MobileInput.getEditText().setText(user.getMobile());
                binding.LoginUserName.setText("Hi there " + user.getName() + "!");
                binding.BirthdayInputBox.getEditText().setText(user.getBirthday());
                Picasso.get().load(user.getProfileImage()).into(binding.ProfileImage);
            }
        });

        binding.DateOFBirthInput.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    setListener, year, month, day
            );
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.BirthdayInputBox.getEditText().setText(i + " / " + i1 + " / " + i2);
                Birthday = i + " / " + i1 + " / " + i2;
            }
        };

        binding.SaveButton.setOnClickListener(view -> {


            String Name = binding.NameBox.getEditText().getText().toString().trim();
            String Email = binding.EmailBox.getEditText().getText().toString().trim();
            String Address = binding.Address.getEditText().getText().toString().trim();
            String Mobile = binding.MobileInput.getEditText().getText().toString().trim();


            if (Name.isEmpty()) {
                Toast.makeText(getActivity(), "Name require", Toast.LENGTH_SHORT).show();
            } else if (Email.isEmpty()) {
                Toast.makeText(getActivity(), "Email require", Toast.LENGTH_SHORT).show();
            } else if (Address.isEmpty()) {
                Toast.makeText(getActivity(), "Address require", Toast.LENGTH_SHORT).show();
            } else if (Mobile.isEmpty()) {
                Toast.makeText(getActivity(), "Mobile require", Toast.LENGTH_SHORT).show();
            } else {
                ShowDialoag();
             //   iosDialog.show();
                viewModel.SendUserPost(Name, Email, Address, Mobile, Birthday)
                        .observe(getActivity(), networkResponse -> {
                            if (networkResponse.getCode() == ResponseCode.SUCCESSCODE) {
                                Utils.hideProgressDialog();
                          //      iosDialog.dismiss();
                            } else if (networkResponse.getCode() == ResponseCode.ErrorCODE) {
                           //     iosDialog.dismiss();
                                Utils.hideProgressDialog();
                            }
                        });
            }


        });

        binding.AddImage.setOnClickListener(view -> {
            opencameradialoag();
        });
    }

    private void goto_splashscreen() {
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void ShowDialoag() {
        Utils.showProgressDialog(requireContext());
       /* iosDialog = new IOSDialog.Builder(getActivity())
                .setTitle("loading")
                .setDimAmount(3)
                .setSpinnerDuration(120)
                .setMessageContentGravity(Gravity.END)
                .setMessageContent("Login your Account")
                .build();*/
    }

    private void opencameradialoag() {
        MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(getActivity(), R.style.PauseDialog);
        PhotocapturedialoagBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.photocapturedialoag, null, false);
        Mbuilder.setView(binding.getRoot());

        alertDialog = Mbuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        binding.GalleryIcon.setOnClickListener(view -> {
            if (ExternalStoresPermission()) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGECODE);
            }

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == IMAGECODE ) {

                alertDialog.dismiss();


                Imageuri = data.getData();
                if(Imageuri != null){
                    IsUploadingImageProgressbar("Uploading profile image");
                //    iosDialog.show();
                    binding.ProfileImage.setImageURI(Imageuri);


                    viewModel.setupprofileimage(Imageuri).observe(this, profileImageDownloadUri -> {
                        if (profileImageDownloadUri != null) {

                            ImageDownloadUri = profileImageDownloadUri.getPosterPath();
                            viewModel.UpdateProfileImage(ImageDownloadUri).observe(this, new Observer<NetworkResponse>() {
                                @Override
                                public void onChanged(NetworkResponse networkResponse) {
                                    if (networkResponse.getCode() == DataManager.SUCCESS_CODE) {
                                        Utils.hideProgressDialog();
                                    //    iosDialog.dismiss();
                                        Toast.makeText(getActivity(), "Profile image upload success", Toast.LENGTH_LONG).show();

                                    } else if (networkResponse.getCode() == DataManager.ERROR_CODE) {
                                        Utils.hideProgressDialog();
                                    //    iosDialog.dismiss();

                                    }
                                }
                            });
                        } else {
                            Utils.hideProgressDialog();
                         //   iosDialog.dismiss();
                        }
                    });
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void IsUploadingImageProgressbar(String Title) {
        Utils.showProgressDialog(requireContext());
     /*   iosDialog = new IOSDialog.Builder(getActivity())
                .setTitle("loading")
                .setDimAmount(3)
                .setSpinnerDuration(120)
                .setMessageContentGravity(Gravity.END)
                .setCancelable(false)
                .setMessageContent(Title)
                .build();
*/
    }

    private boolean ExternalStoresPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONCODE);
            return false;
        }
    }
}


