package com.example.mealmonkey.UI.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.UI.Activity.BookMark;
import com.example.mealmonkey.UI.Activity.MyOrder;
import com.example.mealmonkey.UI.Activity.PaymentDetails;
import com.example.mealmonkey.R;
import com.example.mealmonkey.databinding.MoreBinding;


public class More extends Fragment {

    private MoreBinding binding;

    public More() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.more, container, false);

        init_view();
        return binding.getRoot();
    }

    private void init_view(){
        binding.PaymentDetails.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PaymentDetails.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });

        binding.MyBookMark.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), BookMark.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });

        binding.MyOrder.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MyOrder.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Animatoo.animateSlideLeft(getActivity());
        });
    }
}