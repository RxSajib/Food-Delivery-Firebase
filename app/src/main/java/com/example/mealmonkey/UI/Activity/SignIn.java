package com.example.mealmonkey.UI.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.example.mealmonkey.Data.Response.ResponseCode;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.UI.utils.Utils;
import com.example.mealmonkey.databinding.SigninBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class SignIn extends Fragment {

    private SigninBinding binding;
    private NetworkViewModel viewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth Mauth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.signin, container, false);
        viewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        Mauth = FirebaseAuth.getInstance();


        init_view();
        creating_request();
        loginaccount();
        return binding.getRoot();
    }


    private void loginaccount() {
        binding.LoginButton.setOnClickListener(view -> {

            String Email = binding.EmailInput.getText().toString().trim();
            String Password = binding.PasswordInput.getText().toString().trim();
            if (Email.isEmpty()) {
                Toast.makeText(getActivity(), "Email require", Toast.LENGTH_LONG).show();
            } else if (Password.isEmpty()) {
                Toast.makeText(getActivity(), "Password require", Toast.LENGTH_LONG).show();
            } else {
                ShowDialoag();

                viewModel.LoginEmailPassword(Email, Password)
                        .observe(getActivity(), networkResponse -> {

                            if (networkResponse.getCode() == ResponseCode.SUCCESSCODE) {
                                Utils.hideProgressDialog();
                                networkResponse.setCode(ResponseCode.DEFAULTCODE);
                                goto_homepage();
                            } else if (networkResponse.getCode() == ResponseCode.ErrorCODE) {
                                Utils.hideProgressDialog();
                                networkResponse.setCode(ResponseCode.DEFAULTCODE);
                            }
                        });
            }

        });
        binding.SignUpButton.setOnClickListener(view -> {
            goto_signuppage(new SignUp());
        });
    }

    private void init_view() {
        binding.GoogleButton.setOnClickListener(v -> {
            signIn();
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void creating_request() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    private void ShowDialoag() {
        Utils.showProgressDialog(requireContext());
       /* iosDialog = new IOSDialog.Builder(getActivity())
                .setTitle("loading")
                .setDimAmount(3)
                .setSpinnerDuration(120)
                .setMessageContentGravity(Gravity.END)
                .setCancelable(false)
                .setMessageContent("Login your Account")
                .build();

        iosDialog.show();*/
    }

    private void goto_homepage() {
        Utils.hideProgressDialog();
        Intent intent = new Intent(getActivity(), Home.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(getActivity());
        getActivity().finish();
    }

    private void goto_signuppage(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        ShowDialoag();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        Mauth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String Email = task.getResult().getUser().getEmail();
                        String Phone = task.getResult().getUser().getPhoneNumber();
                        String Name = task.getResult().getUser().getDisplayName();
                        String PhotoUri = task.getResult().getUser().getPhotoUrl().toString();
                        sign_in(Email, Phone, Name, PhotoUri);
                    } else {
                        Toast.makeText(getActivity(), "Error login account", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sign_in(String email, String phone, String name, String photoUri) {
        viewModel.GoogleSignUser(email, name, phone, photoUri)
                .observe(this, new Observer<NetworkResponse>() {
                    @Override
                    public void onChanged(NetworkResponse networkResponse) {
                        if (networkResponse.getCode() == DataManager.SUCCESS_CODE) {
                            Utils.hideProgressDialog();
                            goto_home();
                        } else if (networkResponse.getCode() == DataManager.ERROR_CODE) {
                            Utils.hideProgressDialog();
                        }
                    }
                });
    }

    private void goto_home() {
        Intent intent = new Intent(getActivity(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Animatoo.animateSlideLeft(getActivity());
    }


}