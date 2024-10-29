package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.UI.utils.Utils;
import com.example.mealmonkey.databinding.SignupBinding;

public class SignUp extends Fragment {

    private SignupBinding signupBinding;
    private NetworkViewModel viewModel;
  //  private IOSDialog iosDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signupBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.signup, container, false);
        init_view();
        register_account();

        return signupBinding.getRoot();
    }


    private void register_account() {

        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        signupBinding.SignUpButtonID.setOnClickListener(view -> {
            String Name = signupBinding.NameInput.getText().toString().trim();
            String Email = signupBinding.EmailInput.getText().toString().trim();
            String Mobile = signupBinding.Mobile.getText().toString().trim();
            String Address = signupBinding.Address.getText().toString().trim();
            String Password = signupBinding.Password.getText().toString().trim();
            String ConfirmPassword = signupBinding.ConfrimPassword.getText().toString().trim();

            if (Name.isEmpty()) {
                Toast.makeText(getActivity(), "Name require", Toast.LENGTH_LONG).show();
            } else if (Email.isEmpty()) {
                Toast.makeText(getActivity(), "Email require", Toast.LENGTH_LONG).show();
            } else if (Mobile.isEmpty()) {
                Toast.makeText(getActivity(), "Mobile require", Toast.LENGTH_LONG).show();
            } else if (Address.isEmpty()) {
                Toast.makeText(getActivity(), "Address require", Toast.LENGTH_SHORT).show();
            } else if (Password.isEmpty()) {
                Toast.makeText(getActivity(), "Password require", Toast.LENGTH_SHORT).show();
            } else if (ConfirmPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Confirm Password require", Toast.LENGTH_SHORT).show();
            } else if (!Password.equals(ConfirmPassword)) {
                Toast.makeText(getActivity(), "Password didn't match", Toast.LENGTH_SHORT).show();
            } else {
                ShowDialoag();
              //  iosDialog.show();
                viewModel.SignUpAccount(Name, Email, Mobile, Address, Password, ConfirmPassword)
                        .observe(getActivity(), networkResponse -> {
                            if (networkResponse != null) {
                                if (networkResponse.getCode() == ResponseCode.SUCCESSCODE) {
                                    networkResponse.setCode(ResponseCode.DEFAULTCODE);
                                    goto_home();
                                    Utils.hideProgressDialog();
                             //       iosDialog.dismiss();
                                    Toast.makeText(getActivity(), "SignUp Success", Toast.LENGTH_SHORT).show();
                                } else if (networkResponse.getCode() == ResponseCode.ErrorCODE) {
                                    Utils.hideProgressDialog();
                                  //  iosDialog.dismiss();
                                    networkResponse.setCode(ResponseCode.DEFAULTCODE);
                                }
                            }
                        });
            }
        });

    }

    private void init_view() {
        signupBinding.SignInButton.setOnClickListener(view -> {
            goto_signin(new SignIn());
        });
    }

    private void goto_signin(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void ShowDialoag() {
        Utils.showProgressDialog(requireContext());
     /*   iosDialog = new IOSDialog.Builder(getActivity())
                .setTitle("loading")
                .setDimAmount(3)
                .setSpinnerDuration(120)
                .setMessageContentGravity(Gravity.CENTER)
                .setMessageContent("SignUp your Account")
                .build();*/
    }

    private void goto_home() {
        Intent intent = new Intent(getActivity(), Home.class);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Animatoo.animateSlideLeft(getActivity());
        getActivity().finish();
    }
}